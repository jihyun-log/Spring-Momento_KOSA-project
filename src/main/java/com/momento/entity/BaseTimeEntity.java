//생성 및 사용 : 김지현, 사용 : 정용준
//이 코드는 JPA 엔티티 클래스에서 생성일과 수정일을 관리하기 위한 추상 베이스 시간
//엔티티 클래스인 BaseTimeEntity를 정의합니다. 이 클래스는 다른 엔티티 클래스들이
//공통적으로 사용하는 시간 정보 필드를 정의하고 있습니다.

package com.momento.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 사용하기 위해 어노테이션 추가
@MappedSuperclass //공통 매핑 정보가 필요할 때 사용하는 어노테이션
@Getter @Setter
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime b4Date; //엔티티가 생성되어 저장될 때 시간을 자동으로 저장

    @LastModifiedDate
    private LocalDateTime date; //엔티티의 값을 변경할 때 시간을 자동으로 저장

}