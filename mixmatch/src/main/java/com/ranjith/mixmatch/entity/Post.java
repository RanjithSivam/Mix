package com.ranjith.mixmatch.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="post")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@CreationTimestamp
	private LocalDate createdAt;
	
	private String content;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_profile_id",nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private UserProfile userProfile;
	
	private String imageUrl;
	
	@ElementCollection
	@CollectionTable(name="post_love")
	List<Integer> love = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name="post_share")
	List<Integer> share = new ArrayList<>();
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	@JsonManagedReference
	List<Comment> comment = new ArrayList<>();
}
