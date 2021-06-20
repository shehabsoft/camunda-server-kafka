package org.emu.camunda.web.rest;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.variable.VariableMap;
import org.emu.camunda.utilities.CamundaUtils;
import org.emu.common.dto.bpm.DeploymentDto;
import org.emu.common.dto.bpm.TaskDto;
import org.emu.common.dto.bpm.VariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2021-2022 By Dirac Systems.
 * <p>
 * Created by khalid.nouh on 17/3/2021.
 */
@Log4j2
@RestController
@RequestMapping("/api")
public class CamundaResource {

@Autowired
CamundaUtils camundaUtils;


    @GetMapping("/get")
    public String get() {
        return "this get";
    }

    /**
     * Post Deployment,Create a deployment.
     * POST /deployment/create
     */
    @ApiOperation(value = "Create a deployment in Camunda server")
    /*Optional below comments*/
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Request successful.")}
    )
    @PostMapping(path = "/createDeployment/{deploymentName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DeploymentDto createDeployment(@PathVariable("deploymentName") String deploymentName, @RequestPart("file") MultipartFile multipartFile) throws IOException {
//        log.info(String.format("Create Deployment with deploymentName %s,with file name %s",deploymentName,multipartFile.getOriginalFilename()));
       return camundaUtils.createDeployment(deploymentName,multipartFile);
    }

    @PostMapping(value = "/task/{taskId}/{executionId}/complete",produces = "application/json")
    VariableMap CompletesATaskAndUpdatesProcessVariables(@PathVariable("taskId") String taskId,@PathVariable("executionId") String executionId,@RequestBody Map<String, Object> variables){
       return  camundaUtils.CompletesATaskAndUpdatesProcessVariables(taskId,executionId,variables);
    }

    @GetMapping(value = "/task/{assignee}",produces = "application/json")
   List<TaskDto> getTasksByAssigne(@PathVariable("assignee") String assignee ){
        return  camundaUtils.getTasksByAssigne(assignee);
    }

    @GetMapping(value = "/task/variables/{executionId}",produces = "application/json")
    List<VariableInstance> getTaskvariablesByExecutionId(@PathVariable("executionId") String executionId ){
        return  camundaUtils.getTaskvariablesByExecutionId(executionId);
    }

    @GetMapping("/getAllRunningProcessInstances/{processDefinitionName}")
public List<org.emu.common.dto.bpm.ProcessInstance> getAllRunningProcessInstances(@PathVariable("processDefinitionName") String processDefinitionName) {
        return camundaUtils.getAllRunningProcessInstances(processDefinitionName);
}

}
