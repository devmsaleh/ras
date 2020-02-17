package com.charity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charity.entities.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}
