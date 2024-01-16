package com.techlabs.insurance.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import java.util.Optional;

//import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techlabs.insurance.entity.Agent;
import com.techlabs.insurance.entity.Customer;
import com.techlabs.insurance.entity.FileDB;
import com.techlabs.insurance.entity.InsuranceScheme;
import com.techlabs.insurance.entity.Nominee;
import com.techlabs.insurance.entity.Policy;
import com.techlabs.insurance.entity.UserDetails;
import com.techlabs.insurance.exception.CustomerNotFoundException;
import com.techlabs.insurance.exception.DocumentNotFoundException;
import com.techlabs.insurance.payload.CustomerDto;
import com.techlabs.insurance.payload.DocumentDto;
import com.techlabs.insurance.payload.EmployeeDto;
import com.techlabs.insurance.payload.PolicyDataDto;
import com.techlabs.insurance.repository.AgentRepository;
import com.techlabs.insurance.repository.CustomerRepository;
import com.techlabs.insurance.repository.FileDBRepository;
import com.techlabs.insurance.repository.InsuranceSchemeRepository;
import com.techlabs.insurance.repository.PolicyRepository;
import com.techlabs.insurance.repository.UserDetailsRepository;
import com.techlabs.insurance.service.PolicyService;


@RestController
@RequestMapping("/insuranceapp")
public class PolicyController {

	@Autowired  
	private PolicyService policyService;
	@Autowired
	private PolicyRepository policyRepository;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private AgentRepository agentRepo;
	
	
	@Autowired
	 private UserDetailsRepository userDetailsRepo;
	@Autowired 
	private InsuranceSchemeRepository schemeRepo;
	 
	
	@Autowired  
	private FileDBRepository fileDBRepo;
	
	@PostMapping("/policy/{username}/{schemeId}/{nomineesName}/{nomineesRelation}")
	public  void AddPolicy(@ModelAttribute Policy policy, @PathVariable(name = "username") String username, @PathVariable(name = "schemeId") int schemeId,@PathVariable(name = "nomineesName") String nomineesName,@PathVariable(name = "nomineesRelation") String nomineesRelation,@RequestParam("documentFiles") List<MultipartFile> documentFiles )
	{
		System.out.println("username--->"+policy);
		UserDetails userDetails = userDetailsRepo.findByUsername(username);
		List<Customer> customers = customerRepo.findAll();
		Customer customerdetails=null;
		for (Customer customer : customers) {
            if (customer.getUserdetails().getUserDetailId()== userDetails.getUserDetailId()) 
            {
              customerdetails = customer;
              customerdetails.setDocumentStatus("Uploaded");
            }
        }
		InsuranceScheme scheme = schemeRepo.getById(schemeId);
		System.out.println("schme------>" +scheme);
		
		List<FileDB> documents = new ArrayList<>();
        for (MultipartFile documentFile : documentFiles){
            if ("application/pdf".equals(documentFile.getContentType())) {
            	FileDB document = new FileDB();
                document.setName(documentFile.getOriginalFilename());
                document.setType("pdf");
                try {
					document.setData(documentFile.getBytes());
				} catch (IOException e) {
					
					e.printStackTrace();
				}
                document.setPolicy(policy);
                documents.add(document);
            } else {
                System.out.println("not uploaded");
            }
        }
        Nominee nominee = new Nominee();
        System.out.println("nomineessname-->"+nomineesName+"nomineesrelation--->"+nomineesRelation);
        nominee.setName(nomineesName);
        nominee.setNomineeRelation(nomineesRelation);
        
        List<Nominee> allNominees =new ArrayList<>();
        allNominees.add(nominee);
        policy.setActive(true);
        policy.setFileDB(documents);
        policy.setNominees(allNominees);
        policy.setCustomer(customerdetails);
        policy.setScheme(scheme);    
        
        nominee.setPolicy(policy);
       
        policyRepository.save(policy);
      
        fileDBRepo.saveAll(documents);
	}
	
	
	@PostMapping("/agentpolicy/{username}/{agentUsername}/{schemeId}/{nomineesName}/{nomineesRelation}")
	public  void AddPolicyByAgent(@ModelAttribute Policy policy, @PathVariable(name = "username") String username,  @PathVariable(name = "agentUsername") String agentUsername,@PathVariable(name = "schemeId") int schemeId,@PathVariable(name = "nomineesName") String nomineesName,@PathVariable(name = "nomineesRelation") String nomineesRelation,@RequestParam("documentFiles") List<MultipartFile> documentFiles )
	{
		System.out.println("username--->"+policy);
		UserDetails userDetails = userDetailsRepo.findByUsername(username);
		List<Customer> customers = customerRepo.findAll();
		Customer customerdetails=null;
		for (Customer customer : customers) {
            if (customer.getUserdetails().getUserDetailId()== userDetails.getUserDetailId()) 
            {
              customerdetails = customer;
              customerdetails.setDocumentStatus("Uploaded");
            }
        }
		UserDetails agentUserDetails = userDetailsRepo.findByUsername(agentUsername);
		System.out.println(agentUserDetails.getUsername());
		List<Agent> agents = agentRepo.findAll();
		Agent agentdetails=null;
		for (Agent agent : agents) {
            if (agent.getUserdetails().getUserDetailId()== agentUserDetails.getUserDetailId()) 
            {
            System.out.println("userdetails id--->"+ agent.getUserdetails().getUserDetailId());
              agentdetails = agent;
              System.out.println("agentdetails--->" + agent.getAgentId());
             
            }
        }
		
		
		InsuranceScheme scheme = schemeRepo.getById(schemeId);
//		System.out.println("schme------>" +scheme);
		
		List<FileDB> documents = new ArrayList<>();
        for (MultipartFile documentFile : documentFiles){
            if ("application/pdf".equals(documentFile.getContentType())) {
            	FileDB document = new FileDB();
                document.setName(documentFile.getOriginalFilename());
                document.setType("pdf");
                try {
					document.setData(documentFile.getBytes());
				} catch (IOException e) {
					
					e.printStackTrace();
				}
                document.setPolicy(policy);
                documents.add(document);
            } else {
                System.out.println("not uploaded");
            }
        }
        Nominee nominee = new Nominee();
//        System.out.println("nomineessname-->"+nomineesName+"nomineesrelation--->"+nomineesRelation);
        nominee.setName(nomineesName);
        nominee.setNomineeRelation(nomineesRelation);
        
        List<Nominee> allNominees =new ArrayList<>();
        allNominees.add(nominee);
        policy.setActive(true);
        policy.setFileDB(documents);
        policy.setNominees(allNominees);
        policy.setCustomer(customerdetails);
        policy.setAgent(agentdetails);
        policy.setScheme(scheme);    
        
        nominee.setPolicy(policy);
       
        policyRepository.save(policy);
      
        fileDBRepo.saveAll(documents);
	}
	@GetMapping("/policy")
	private ResponseEntity<Page<PolicyDataDto>> getAllPolicy (@RequestParam Map<String, String>params)
	{
		Page<PolicyDataDto> policyPage = null;
		int pagenumber =0;
		int pagesize = 30;
		if(params.isEmpty()){
			policyService.getAllPolicy();
		}
		else{
			if(params.containsKey("pagenumber")){
				pagenumber = Integer.parseInt(params.get("pagenumber"));
			}
			if (params.containsKey("pagesize")) {   
				 pagesize = Integer.parseInt(params.get("pagesize"));
			 }
		}
		policyPage = policyService.getAllPolicyPageWise(pagenumber, pagesize);
		
		if(policyPage.getTotalElements()==0){
			 throw new CustomerNotFoundException();
		}
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(policyPage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(policyPage);
	}
	
	@GetMapping("/getdocuments/{policyNumber}")
	public ResponseEntity<List<DocumentDto>> getdocument(@PathVariable(name="policyNumber") Integer policyNumber){
		
		List<DocumentDto> documentDtos=policyService.getalldocument(policyNumber);
		
		if(documentDtos.size()==0) {
			throw new DocumentNotFoundException();
		}
		
		
		return ResponseEntity.ok(documentDtos);
		
	}
	@GetMapping("/policydetails")
	private ResponseEntity<Page<PolicyDataDto>> getAllPolicyByUsername (@RequestParam Map<String, String>params)
	{
		Page<PolicyDataDto> policyPage = null;
		int pagenumber =0;
		int pagesize = 30;
		String username =null;
		if(params.isEmpty()){
			policyService.getAllPolicy();
		}
		else{
			if(params.containsKey("pagenumber")){
				pagenumber = Integer.parseInt(params.get("pagenumber"));
			}
			if (params.containsKey("pagesize")) {   
				 pagesize = Integer.parseInt(params.get("pagesize"));
			 }
			if(params.containsKey("username"))   {
				 username = params.get("username");
			 }
		}
		policyPage = policyService.getAllPolicyPageWiseAndUsername(pagenumber, pagesize,username);
//		System.out.println("page no is--->"+pagenumber+"--->pagesize--->"+pagesize+"---username--->"+username);
		if(policyPage.getTotalElements()==0){
			 throw new CustomerNotFoundException();
		}
//		System.out.println("total no of policy"+policyPage.getTotalElements());
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(policyPage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(policyPage);
	}
	
	
	@PutMapping("/updatestatus/{policyNumber}/{status}")
	public  ResponseEntity<String> UpdateCustomerdetails(@PathVariable(name = "policyNumber") int policyNumber,@PathVariable(name = "status") String status )
	{
		String response = policyService.updatePolicyStatus(policyNumber, status);
		return new ResponseEntity<>(response,HttpStatus.CREATED); 
	}
	
	
	@GetMapping("/policyagent")
	private ResponseEntity<Page<PolicyDataDto>> getAllAgentPolicy ( @RequestParam Map<String, String>params)
	{
		Page<PolicyDataDto> policyPage = null;
		int pagenumber =0;
		int pagesize = 30;
		String username =null;
		if(params.isEmpty()){
			policyService.getAllPolicy();
		}
		else{
			if(params.containsKey("pagenumber")){
				pagenumber = Integer.parseInt(params.get("pagenumber"));
			}
			if (params.containsKey("pagesize")) {   
				 pagesize = Integer.parseInt(params.get("pagesize"));
			 }
			if(params.containsKey("username"))   {
				 username = params.get("username");
			 }
		}
		policyPage = policyService.getAllPolicyByPageWiseAndAgent(pagenumber, pagesize,username);
		
		if(policyPage.getTotalElements()==0){
			 throw new CustomerNotFoundException();
		}
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(policyPage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(policyPage);
		
	}
}
