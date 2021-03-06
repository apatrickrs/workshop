package com.workshop.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.workshop.domain.Post;
import com.workshop.domain.User;
import com.workshop.dto.AuthorDTO;
import com.workshop.dto.CommentDTO;
import com.workshop.repository.PostRepository;
import com.workshop.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository PostRepository;

	@Override
	public void run(String... args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		userRepository.deleteAll();
		PostRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(maria, alex, bob));

		Post post1 = new Post(null, sdf.parse("21/03/2019"), "Partiu viagem", "São Paulo, abraço", new AuthorDTO(alex));
		Post post2 = new Post(null, sdf.parse("21/03/2019"), "gogo viagem", "RJ, abraço", new AuthorDTO(alex));

		CommentDTO c1 = new CommentDTO("Vai com Deus", sdf.parse("21/03/2019"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveita", sdf.parse("22/03/2019"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Chama no desk", sdf.parse("23/03/2019"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		PostRepository.saveAll(Arrays.asList(post1, post2));
		
		alex.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(alex);
	}

}
