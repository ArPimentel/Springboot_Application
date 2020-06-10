package com.arturPimentelApp.applicationspring.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ChangePassword {
	@NotNull 
	private Long id;

	@NotBlank(message="Le mot de passe actuel ne doit pas être vide")
	private String currentPassword;

	@NotBlank(message="Le nouveau mot de passe ne doit pas être vide")
	private String newPassword;

	@NotBlank(message="Confirmer que le mot de passe ne doit pas être vide")
	private String confirmPassword;

	public ChangePassword() { }
	public ChangePassword(Long id) {this.id = id;}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}