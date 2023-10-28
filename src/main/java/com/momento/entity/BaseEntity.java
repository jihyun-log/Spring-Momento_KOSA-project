//생성 및 사용 : 김지현, 사용 : 정용준
//이 코드는 JPA (Java Persistence API) 엔티티 클래스들의 공통 속성을 관리하기
//위한 추상 베이스 엔티티 클래스인 BaseEntity를 정의합니다. 이 클래스는 다른 엔티티
//클래스들이 공통적으로 사용하는 필드를 정의하고 있습니다.

package com.momento.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public abstract class BaseEntity extends BaseTimeEntity{

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

}