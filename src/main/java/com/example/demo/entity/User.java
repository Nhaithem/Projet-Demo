package com.example.demo.entity;


import java.util.Collection;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import com.example.demo.entity.Product;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter // lombok 
@Setter //lombok 
@AllArgsConstructor //lombok 
@NoArgsConstructor //lombok 
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String fullName;

    
    private String email;

    
    private String password;

    private int loginAttempts = 0;

    private Boolean isEnabled = false;

    private Boolean isLocked = false ;
  
   
    private Role role ;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE) // supp user --> sup produit 
    private List<Product> produit;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
    /*
    public Long getId() {
  		return id;
  	}

  	public void setId(Long id) {
  		this.id = id;
  	}

  	public String getFullName() {
  		return fullName;
  	}

  	public void setFullName(String fullName) {
  		this.fullName = fullName;
  	}

  	public String getEmail() {
  		return email;
  	}

  	public void setEmail(String email) {
  		this.email = email;
  	}

  	public int getLoginAttempts() {
  		return loginAttempts;
  	}

  	public void setLoginAttempts(int loginAttempts) {
  		this.loginAttempts = loginAttempts;
  	}

  	public Boolean getIsEnabled() {
  		return isEnabled;
  	}

  	public void setIsEnabled(Boolean isEnabled) {
  		this.isEnabled = isEnabled;
  	}

  	public Boolean getIsLocked() {
  		return isLocked;
  	}

  	public void setIsLocked(Boolean isLocked) {
  		this.isLocked = isLocked;
  	}

  	public List<Role> getRoles() {
  		return role;
  	}

  	public void setRoles(List<Role> roles) {
  		this.role = role;
  	}

  	public List<Product> getProduit() {
  		return produit;
  	}

  	public void setProduit(List<Product> produit) {
  		this.produit = produit;
  	}

  	public void setPassword(String password) {
  		this.password = password;
  	}
  	*/
}
