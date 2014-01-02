package edu.cmpe281.project;

import java.util.Scanner;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.s3.AmazonS3Client;

public class Transcoder {
	AWSCredentials credentials;
	AmazonS3Client s3Client;
	AmazonElasticTranscoderClient transcoderClient;
	BucketHandler bucketHandler;
	PipelineHandler pipelineHandler;
	JobHandler jobHandler;
	
	private void init()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your access key: ");
		String accessKey = scanner.nextLine();
		System.out.println("Enter your secret key: ");
		String secretKey = scanner.nextLine();
		credentials = new BasicAWSCredentials(accessKey, secretKey);
		s3Client = new AmazonS3Client(credentials);
		transcoderClient = new AmazonElasticTranscoderClient(credentials);
		bucketHandler = new BucketHandler(s3Client);
		pipelineHandler = new PipelineHandler(transcoderClient);
		jobHandler = new JobHandler(transcoderClient);
		scanner.close();
	}
	
	private void bucketMenu()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter 1 to create new bucket.");
		System.out.println("Enter 2 to list existing buckets.");
		System.out.println("Enter 3 to upload a file to bucket.");
		System.out.println("Enter 4 to download a file from bucket.");
		String key = scanner.nextLine();
		switch(Integer.parseInt(key))
		{
			case 1:
			{
				bucketHandler.createBucket();
				break;
			}
			case 2:
			{
				bucketHandler.listBuckets();
				break;
			}
			case 3:
			{
				bucketHandler.uploadFile();
				break;
			}
			case 4:
			{
				bucketHandler.downloadFile();
				break;
			}
			default:
			{
				break;
			}
		}
		scanner.close();
	}
	
	private void pipelineMenu()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter 1 to create new pipeline.");
		System.out.println("Enter 2 to list existing pipeline.");
		System.out.println("Enter 3 to delete pipeline.");
		String key = scanner.nextLine();
		switch(Integer.parseInt(key))
		{
			case 1:
			{
				pipelineHandler.createPipeline();
				break;
			}
			case 2:
			{
				pipelineHandler.listPipelines();
				break;
			}
			case 3:
			{
				pipelineHandler.deletePipeline();
				break;
			}
			default:
			{
				break;
			}
		}
		scanner.close();
	}
	
	private void jobMenu()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter 1 to create new job.");
		System.out.println("Enter 2 to list existing jobs.");
		System.out.println("Enter 3 to cancel a job.");
		String key = scanner.nextLine();
		switch(Integer.parseInt(key))
		{
			case 1:
			{
				jobHandler.createJob();
				break;
			}
			case 2:
			{
				jobHandler.listJobs();
				break;
			}
			case 3:
			{
				jobHandler.cancelJob();
				break;
			}
			default:
				break;
		}
	}
	

	public static void main(String args[])
	{
		Transcoder transcoder = new Transcoder();
		//initialization
		transcoder.init();
		
		//Main menu
		while(true)
		{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter 1 to access buckets menu.");
			System.out.println("Enter 2 to access pipelines menu.");
			System.out.println("Enter 3 to access jobs menu.");
			String key = scanner.nextLine();
			switch(Integer.parseInt(key))
			{
				case 1:
				{
					transcoder.bucketMenu();
					break;
				}
				case 2:
				{
					transcoder.pipelineMenu();
					break;
				}
				case 3:
				{
					transcoder.jobMenu();
					break;
				}
					
				default:
				{
					System.exit(0);
					break;
				}
			}
			scanner.close();
		}
	}
}
