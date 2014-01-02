package edu.cmpe281.project;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;

public class BucketHandler {
	
	AmazonS3Client s3Client;
	
	public BucketHandler(AmazonS3Client s3) {
		
		this.s3Client = s3;
	}

	public void createBucket()
	{
		try
		{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter name for new bucket: ");
			String bucketName = scanner.nextLine();
			Bucket newBucket =	s3Client.createBucket(bucketName);
			System.out.println("Bucket created :"+newBucket.getName());
			scanner.close();
		}
		catch(AmazonS3Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	public void listBuckets()
	{
		try
		{
			List<Bucket> buckets = s3Client.listBuckets();
			Iterator<Bucket> iBuckets = buckets.listIterator();
			while(iBuckets.hasNext())
			{
				System.out.println(iBuckets.next().getName());
			}
		}
		catch(AmazonS3Exception ex)
		{
			System.out.println(ex);
		}
		
	}
	
	public void uploadFile()
	{
		try
		{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter name of bucket: ");
			String bucketName = scanner.nextLine();
			System.out.println("Enter path of file: ");
			String filePath = scanner.nextLine();
			System.out.println("Enter key for file: ");
			String key = scanner.nextLine();
			
			s3Client.putObject(bucketName, key, new File(filePath));
			System.out.println("File is uploaded to bucket.");
			scanner.close();
		}
		catch(AmazonS3Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	public void downloadFile()
	{
		try
		{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter name of bucket: ");
			String bucketName = scanner.nextLine();
			System.out.println("Enter key for file: ");
			String key = scanner.nextLine();
			System.out.println("Enter downlaod path for file: ");
			String filePath = scanner.nextLine();
			S3Object object = s3Client.getObject(bucketName, key);
			//TODO - Downloading file
			scanner.close();
		}
		catch(AmazonS3Exception ex)
		{
			System.out.println(ex);
		}
	}
}
