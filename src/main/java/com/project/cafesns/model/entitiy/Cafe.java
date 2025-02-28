package com.project.cafesns.model.entitiy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.cafesns.model.dto.register.RegisterOwnerRequestDto;
import com.project.cafesns.model.dto.cafe.ModifyCafeRequestDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cafename;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressdetail;

    @Column(nullable = false)
    private String zonenum;

    @Column
    private String intro;

    @Column
    private String notice;

    @Column
    private Boolean delivery;

    @Column
    private String opentime;

    @Column
    private String closetime;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "ownerid")
    @JsonBackReference(value = "user-cafe-FK")
    private User user;

    @OneToMany(mappedBy = "cafe", orphanRemoval = true)
    @JsonManagedReference(value = "menu-cafe-FK")
    List<Menu> menuList;

    @OneToMany(mappedBy = "cafe", orphanRemoval = true)
    @JsonManagedReference(value = "cafe-post-FK")
    List<Post> postList;


    //사장이 바로 등록하는(카페 페이지 생성) 생성자
    @Builder
    public Cafe(RegisterOwnerRequestDto registerOwnerRequestDto, User user){
        this.cafename = user.getBusinessname();
        this.address = registerOwnerRequestDto.getAddress();
        this.addressdetail = registerOwnerRequestDto.getAddressdetail();
        this.zonenum = registerOwnerRequestDto.getZonenum();
        this.latitude = registerOwnerRequestDto.getLatitude();
        this.longitude = registerOwnerRequestDto.getLongitude();
        this.user = user;
    }
    //신청내역을 통한 카페 페이지 생성 생성자
    public Cafe(Register register){
        this.cafename = register.getCafename();
        this.address = register.getAddress();
        this.addressdetail = register.getAddressdetail();
        this.zonenum = register.getZonenum();
        this.intro = null;
        this.notice = null;
        this.delivery = null;
        this.opentime = null;
        this.closetime = null;
        this.latitude =register.getLatitude();
        this.longitude = register.getLongitude();
        this.user = null;
    }

    public void changeCafe(ModifyCafeRequestDto modifyCafeRequestDto){
        this.address = modifyCafeRequestDto.getAddress();
        this.addressdetail = modifyCafeRequestDto.getAddressdetail();
        this.zonenum = modifyCafeRequestDto.getZonenum();
        this.intro = modifyCafeRequestDto.getIntro();
        this.notice = modifyCafeRequestDto.getNotice();
        this.delivery = modifyCafeRequestDto.getDelivery();
        this.opentime = modifyCafeRequestDto.getOpentime();
        this.closetime = modifyCafeRequestDto.getClosetime();
        this.latitude = modifyCafeRequestDto.getLatitude();
        this.longitude = modifyCafeRequestDto.getLongitude();
    }

    public void getOwnership(User user) {
        this.user = user;
    }
}
