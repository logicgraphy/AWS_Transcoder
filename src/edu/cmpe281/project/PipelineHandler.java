package edu.cmpe281.project;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.CreatePipelineRequest;
import com.amazonaws.services.elastictranscoder.model.CreatePipelineResult;
import com.amazonaws.services.elastictranscoder.model.DeletePipelineRequest;
import com.amazonaws.services.elastictranscoder.model.ListPipelinesRequest;
import com.amazonaws.services.elastictranscoder.model.ListPipelinesResult;
import com.amazonaws.services.elastictranscoder.model.Notifications;
import com.amazonaws.services.elastictranscoder.model.Pipeline;

public class PipelineHandler {
	AmazonElasticTranscoderClient transcoderClient;
	
	public PipelineHandler(AmazonElasticTranscoderClient transcoderClient) {
		this.transcoderClient = transcoderClient;
	}

	public void createPipeline() throws AmazonServiceException, AmazonClientException
	{
		try
		{
			CreatePipelineRequest createPipelineRequest = new CreatePipelineRequest();
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter name for new pipeline: ");
			String pipelineName = scanner.nextLine();
			createPipelineRequest.setName(pipelineName);
			System.out.println("Enter name of input bucket: ");
			String inputBucket = scanner.nextLine();
			createPipelineRequest.setInputBucket(inputBucket);
			System.out.println("Enter name of output bucket: ");
			String outputBucket = scanner.nextLine();
			createPipelineRequest.setOutputBucket(outputBucket);
			createPipelineRequest.setRole("Elastic_Transcoder_Default_Role");
			//TODO - handle notifications
			//notifications.setCompleted("arn:aws:sns:us-east-1::Completed");
	        //notifications.setError("arn:aws:sns:us-east-1::error");
	        //notifications.setWarning("arn:aws:sns:us-east-1::Warning");
	        //notifications.setProgressing("arn:aws:sns:us-east-1::Progress");
			//createPipelineRequest.setNotifications(notifications);
			CreatePipelineResult pipeline = transcoderClient.createPipeline(createPipelineRequest);
			System.out.println("Created pipeline: "+pipeline.getPipeline().getId());
			scanner.close();
		}
		catch(AmazonClientException ex)
		{
			System.out.println(ex);
		}
	}
	
	public void deletePipeline()
	{
		try
		{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter pipeline name to delete: ");
			String pipelineName = scanner.nextLine();
			DeletePipelineRequest deletePipelineRequest = new DeletePipelineRequest();
			deletePipelineRequest.setId(pipelineName);
			transcoderClient.deletePipeline(deletePipelineRequest);
			System.out.println("Pipeline "+pipelineName+" is deleted.");
			scanner.close();
		}
		catch(AmazonClientException ex)
		{
			System.out.println(ex);
		}
	}
	
	public void listPipelines(){
		try
		{
			ListPipelinesResult listPipelinesResult = transcoderClient.listPipelines();
			List<Pipeline> listPipeline = listPipelinesResult.getPipelines();
			Iterator<Pipeline> iPipeline = listPipeline.iterator();
			while(iPipeline.hasNext())
			{
				System.out.println(iPipeline.next().getName());
			}
		}
		catch(AmazonClientException ex)
		{
			System.out.println(ex);
		}
	}
	
}
