package edu.cmpe281.project;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.CancelJobRequest;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.CreateJobResult;
import com.amazonaws.services.elastictranscoder.model.Job;
import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.amazonaws.services.elastictranscoder.model.ListJobsByPipelineRequest;
import com.amazonaws.services.elastictranscoder.model.ListJobsByPipelineResult;
import com.amazonaws.services.elastictranscoder.model.ListJobsByStatusRequest;
import com.amazonaws.services.elastictranscoder.model.ListJobsByStatusResult;

public class JobHandler {
	AmazonElasticTranscoderClient transcoderClient;
	
	public JobHandler(AmazonElasticTranscoderClient transcoderClient)
	{
		this.transcoderClient = transcoderClient;
	}

	public void createJob()
	{
		try
		{
			CreateJobRequest createJobRequest = new CreateJobRequest();
			CreateJobOutput createJobOutput = new CreateJobOutput();
			JobInput jobInput = new JobInput();
			Scanner scanner = new Scanner(System.in);
			
			System.out.println("Enter name of pipleline: ");
			String pipeline = scanner.nextLine();
			createJobRequest.setPipelineId(pipeline);
			System.out.println("Enter S3 key for input video file: ");
			String inputKey = scanner.nextLine();
			jobInput.setKey(inputKey);
			System.out.println("Enter transcoding preset ID: ");
			String presetID = scanner.nextLine();
			createJobOutput.setPresetId(presetID);
			System.out.println("Enter S3 key for output video file: ");
			String outputKey = scanner.nextLine();
			createJobOutput.setKey(outputKey);
			
			createJobRequest.setInput(jobInput);
			createJobRequest.setOutput(createJobOutput);
			CreateJobResult result = transcoderClient.createJob(createJobRequest);
			System.out.println("Job is created: "+result.getJob().toString());
			scanner.close();
		}
		catch(AmazonClientException ex)
		{
			System.out.println(ex);
		}
	}
	
	public void cancelJob()
	{
		try
		{
			CancelJobRequest cancelJobRequest = new CancelJobRequest();
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter job ID to cancel: ");
			String jobID = scanner.nextLine();
			cancelJobRequest.setId(jobID);
			
			transcoderClient.cancelJob(cancelJobRequest);
			System.out.println("Job is canceled");
			scanner.close();
		}
		catch(AmazonClientException ex)
		{
			System.out.println(ex);
		}
	}
	
	public void listJobs()
	{
		try
		{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter 1 to list jobs by pipeline.");
			System.out.println("Enter 2 to list jobs by status.");
			String type = scanner.nextLine();	
			List<Job> listJob = null;
			switch(Integer.parseInt(type))
			{
				case 1:
				{
					ListJobsByPipelineRequest listJobsByPipelineRequest = new ListJobsByPipelineRequest();
					System.out.println("Enter the pipeline ID: ");
					String pipelineID = scanner.nextLine();	
					listJobsByPipelineRequest.setPipelineId(pipelineID);
					ListJobsByPipelineResult result = transcoderClient.listJobsByPipeline(listJobsByPipelineRequest);
					listJob = result.getJobs();
					break;
				}
				case 2:
				{
					ListJobsByStatusRequest listJobsByStatusRequest = new ListJobsByStatusRequest();
					System.out.println("Enter the job status: ");
					String status = scanner.nextLine();	
					listJobsByStatusRequest.setStatus(status);
					ListJobsByStatusResult result = transcoderClient.listJobsByStatus(listJobsByStatusRequest);
					listJob = result.getJobs();
					break;
				}
				default:
				{
					break;
				}
			}
			
			Iterator<Job> iJob = listJob.iterator();
			while(iJob.hasNext())
			{
				Job job = iJob.next();
				System.out.println(job.getId() + " " +job.getStatus());
				
			}
			scanner.close();
		}
		catch(AmazonClientException ex)
		{
			System.out.println(ex);
		}
	}
}
