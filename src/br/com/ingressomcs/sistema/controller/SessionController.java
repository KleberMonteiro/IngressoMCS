package br.com.ingressomcs.sistema.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.ingressomcs.sistema.dominio.entidades.Permissao;
import br.com.ingressomcs.sistema.dominio.entidades.Usuario;

@Named
@Scope("session")
public class SessionController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(SessionController.class);

	private Usuario usuario;	
	private boolean admin = false;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		usuario = getUsuarioAutenticado();
		
//		for(Permissao permissao : usuario.getPermissoes()){
//			if(permissao.getNome().equals("ROLE_ADMIN"))
				admin = true;
//		}
	}

	public Usuario getUsuarioAutenticado() {
		Usuario usuario = null;

		try {
			SecurityContext context = SecurityContextHolder.getContext();
			if (context instanceof SecurityContext) {
				Authentication authentication = context.getAuthentication();
				if (authentication instanceof Authentication) {
					usuario = (Usuario) authentication.getPrincipal();
				}
			}
		} catch (ClassCastException e) {
			logger.debug("Nenhum usuário logado.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getClass() + ": " + e.getMessage());
		}

		return usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}	

}
