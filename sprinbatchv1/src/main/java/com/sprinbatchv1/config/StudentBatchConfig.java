package com.sprinbatchv1.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.sprinbatchv1.entity.Student;
import com.sprinbatchv1.repository.StudentRepository;

@Configuration
public class StudentBatchConfig {
	
	@Autowired
	private StudentRepository studentRepository;
	
	 @Autowired
	   private DataSource dataSource;
	
	@Bean
	public FlatFileItemReader<Student> fileReader(){
		FlatFileItemReader<Student> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource("src\\main\\resources\\students_5000_records.csv"));
		reader.setName("csv-reader");
		reader.setLinesToSkip(1);
		reader.setLineMapper(lineMapper());
		return reader;
	}

	@Bean
	public LineMapper<Student> lineMapper() {
		
		DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();
		
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(",");
		tokenizer.setStrict(false);
		tokenizer.setNames("FirstName","LastName","FatherName","MotherName",
				"University","Specialization","Email","MobileNumber",
				"City","Country");
		
		BeanWrapperFieldSetMapper<Student> mapper = new BeanWrapperFieldSetMapper<>();
		mapper.setTargetType(Student.class);
		
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(mapper);
		
		return lineMapper;
	}
	
	@Bean
	public StudentItemProcessor itemProcessor() {
		return new StudentItemProcessor();
	}
	
	/*@Bean
	public RepositoryItemWriter<Student> writer(){
		RepositoryItemWriter<Student> writer = new RepositoryItemWriter<>();
		writer.setRepository(studentRepository);
		writer.setMethodName("save");
		return writer;
	}*/
	
    @Bean
    public JdbcBatchItemWriter<Student> jdbcWriter() {
        JdbcBatchItemWriter<Student> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("""
            INSERT INTO student (first_name, last_name, father_name, mother_name,
                university, specialization, email, mobile_number, city, country)
            VALUES (:firstName, :lastName, :fatherName, :motherName,
                :university, :specialization, :email, :mobileNumber, :city, :country)
        """);
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }

	@Bean
	public Job job(JobRepository jobRepository, Step step) {
		return new JobBuilder("job-importPersons", jobRepository)
				.start(step)
				.build();
	}
	
	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		 return new StepBuilder("step-csv",jobRepository)
				 .<Student,Student>chunk(100,transactionManager)
				 .reader(fileReader())
				 .processor(itemProcessor())
				 .writer(jdbcWriter())
				 .taskExecutor(taskExecutor())
				 .build();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
		simpleAsyncTaskExecutor.setConcurrencyLimit(100);
		return simpleAsyncTaskExecutor;
	}
}
