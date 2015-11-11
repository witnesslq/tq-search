/*
 * Copyright (c) 2015 taqu.cn
 * All rights reserved.
 */
package cn.taqu.search.web.api.vo;

/**
 * 设备信息
 * @author:laikunzhen
 */
public class RequestBaseParams {
	
	private String service;
	
	private String method;
	
	private Long timestamp;//时间戳

	private Long app_version;//应用版本号，旧版带点号，如 5.0.0.1；新版为数字，如 5001
	
	private String channel;//渠道
	
	private Integer appcode;//应用编码
	
	private String alias;//别名
	
	private Integer gender;//性别 ?
	
	private String os;//系统名称
	
	private String os_version;//系统版本
	
	private String mac;//手机的mac地址
	
	private String token;//手机的唯一编码
	
	private String access;//网络情况，传数字，1:2G 2:3G 3:wifi 4:4G
	
	private String platform_name;//平台名称
	
	private Integer platform_id;//平台编码 1为android 2为ios 3为ipad
	
	private String project_name;//项目名称，默认为taqu
	
	private String partner;//合作方参数
	
	private String height;//高度
	
	private String width;//宽度
	
	private String longtitude;//经度
	
	private String latitude;//纬度
	
	private String ip;//客户端ip

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getAppcode() {
		return appcode;
	}

	public void setAppcode(Integer appcode) {
		this.appcode = appcode;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOs_version() {
		return os_version;
	}

	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPlatform_name() {
		return platform_name;
	}

	public void setPlatform_name(String platform_name) {
		this.platform_name = platform_name;
	}

	public Integer getPlatform_id() {
		return platform_id;
	}

	public void setPlatform_id(Integer platform_id) {
		this.platform_id = platform_id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Long getApp_version() {
		return app_version;
	}

	public void setApp_version(Long app_version) {
		this.app_version = app_version;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
}
