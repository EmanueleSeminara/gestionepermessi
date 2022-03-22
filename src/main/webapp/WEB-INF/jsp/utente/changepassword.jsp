<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	 <style>
		    .error_field {
		        color: red; 
		    }
		</style>
	   
	   <title>Modifica Elemento</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<%-- se l'attributo in request ha errori --%>
					<spring:hasBindErrors  name="edit_utente_attr">
						<%-- alert errori --%>
						<div class="alert alert-danger " role="alert">
							Attenzione!! Sono presenti errori di validazione
						</div>
					</spring:hasBindErrors>
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Inserisci nuovo elemento</h5> 
				    </div>
				    <div class='card-body'>
		
							<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
		
		
							<form:form modelAttribute="change_password_attr" method="post" action="${pageContext.request.contextPath}/utente/applychangepassword" novalidate="novalidate" class="row g-3">
								<input type="hidden" name="id" value="${change_password_attr.id }">
							
								<div class="col-md-3">
							<label for="passwordAttuale" class="form-label">Password attuale <span
								class="text-danger">*</span></label>
							<spring:bind path="passwordAttuale">
								<input type="password"
									class="form-control ${status.error ? 'is-invalid' : ''}"
									name="passwordAttuale" id="passwordAttuale" placeholder="Inserire password attuale"
									required>
							</spring:bind>
							<form:errors path="passwordAttuale" cssClass="error_field" />
						</div>
			

						<div class="col-md-3">
							<label for="password" class="form-label">Password <span
								class="text-danger">*</span></label>
							<spring:bind path="password">
								<input type="password"
									class="form-control ${status.error ? 'is-invalid' : ''}"
									name="password" id="password" placeholder="Inserire Password"
									required>
							</spring:bind>
							<form:errors path="password" cssClass="error_field" />
						</div>

						<div class="col-md-3">
							<label for="confermaPassword" class="form-label">Conferma
								Password <span class="text-danger">*</span>
							</label>
							<spring:bind path="confermaPassword">
								<input type="password"
									class="form-control ${status.error ? 'is-invalid' : ''}"
									name="confermaPassword" id="confermaPassword"
									placeholder="Confermare Password" required>
							</spring:bind>
							<form:errors path="confermaPassword" cssClass="error_field" />
						</div>
								
								
								<div class="col-12">
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
									<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
									<a class="btn btn-outline-secondary ml-2" href="${pageContext.request.contextPath }/utente">Torna alla Lista</a>
								</div>
		
						</form:form>
  
				    
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>		
					  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>