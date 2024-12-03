package th.co.locus.jlo.workflow.tracking;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.workflow.tracking.bean.WorkflowTrackingBean;

@RestController
@RequestMapping("/api/workflow-tracking")
public class WorkflowTrackingController {
	 @Autowired
	 private WorkflowTrackingService service;
	 
	// Create or Update a Workflow Tracking
	    @PostMapping
	    public ResponseEntity<WorkflowTrackingBean> createOrUpdateTracking(@RequestBody WorkflowTrackingBean tracking) {
	    	WorkflowTrackingBean savedTracking = service.saveTracking(tracking);
	        return new ResponseEntity<>(savedTracking, HttpStatus.CREATED);
	    }

	    // Get all Workflow Tracking records
	    @GetMapping
	    public List<WorkflowTrackingBean> getAllTracking() {
	        return service.getAllTracking();
	    }

	    // Get Workflow Tracking by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<WorkflowTrackingBean> getTrackingById(@PathVariable Integer id) {
	        Optional<WorkflowTrackingBean> tracking = service.getTrackingById(id);
	        return tracking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    // Delete Workflow Tracking by ID
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteTracking(@PathVariable Integer id) {
	        service.deleteTracking(id);
	        return ResponseEntity.noContent().build();
	    }
}
