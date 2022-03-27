<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	 <style>
		    .error_field {
		        color: red; 
		    }
		    p{
		    	margin-bottom: .5rem;
		    }
		</style>
	   
	   <title>Inserisci Nuova Richiesta Permesso</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<%-- se l'attributo in request ha errori --%>
					<spring:hasBindErrors  name="insert_richiestaPermesso_attr">
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
				        <h5>Inserisci nuova Richiesta Permesso</h5> 
				    </div>
				    <div class='card-body'>
		
							<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
		
		
							<form:form enctype="multipart/form-data" modelAttribute="insert_richiestaPermesso_attr" method="post" action="save" novalidate="novalidate" class="row g-3">
								
								<div class="col-md-2">
									<label for="tipoPermesso" class="form-label">Tipo Permesso </label>
									<spring:bind path="tipoPermesso">
								    <select class="form-select" id="tipoPermesso" name="tipoPermesso" >
								    	<option value="" selected> - Selezionare - </option>
								      	<option value="FERIE" >FERIE</option>
								      	<option value="MALATTIA"  >MALATTIA</option>
								    </select>
								    </spring:bind>
								    <form:errors  path="tipoPermesso" cssClass="error_field" />
								</div>
								
								<div id="codiceCertificato" class="col-md-3 d-none">
									<label for="codiceCertificato" class="form-label">Codice Certificato</label>
									<spring:bind path="codiceCertificato">
										<input type="text" name="codiceCertificato" id="codiceCertificato" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire il codice certificato" value="${insert_richiestaPermesso_attr.codiceCertificato }" required>
									</spring:bind>
									<form:errors  path="codiceCertificato" cssClass="error_field" />
								</div>
								<div id="attachment" class="col-md-6 datiMalattia  d-none">
									  <label for="allegato" class="form-label">Allegato <span class="text-danger">*</span></label>
									  <input class="form-control" type="file" id="allegato" name="file" required>
								</div>
								
								<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDate" type='date' value='${insert_richiestaPermesso_attr.dataInizio}' />
								<div class="col-md-3">
									<label for="dataInizio" class="form-label">Data Inizio <span class="text-danger">*</span></label>
	                        		<spring:bind path="dataInizio">
		                        		<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dataInizio" type="date" placeholder="dd/MM/yy"
		                            		title="formato : gg/mm/aaaa"  name="dataInizio" required 
		                            		value="${parsedDate}" >
		                            </spring:bind>
	                            	<form:errors  path="dataInizio" cssClass="error_field" />
								</div>
								
								<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDate2" type='date' value='${insert_richiestaPermesso_attr.dataFine}' />
								<div id="dataFineDiv" class="col-md-3">
									<label for="" class="form-label">Data Fine</label>
	                        		<spring:bind path="dataFine">
		                        		<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dataFine" type="date" placeholder="dd/MM/yy"
		                            		title="formato : gg/mm/aaaa"  name="dataFine" required 
		                            		value="${parsedDate2}" >
		                            </spring:bind>
	                            	<form:errors  path="dataFine" cssClass="error_field" />
								</div>
								
								<div class="col-md-3">
									<div class="form-check">
										<input class="form-check-input" name="giornoSingolo" type="checkbox" id="giornoSingolo" >
										<label class="form-check-label" for="giornoSingolo" >
											Giorno singolo
										</label>
									</div>
								</div>
								
								<div class="col-md-6">
									<label for="note" class="form-label">Note</label>
									<spring:bind path="note">
										<textarea name="note" id="note" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire le note" required>${insert_richiestaPermesso_attr.note }</textarea>
									</spring:bind>
									<form:errors  path="note" cssClass="error_field" />
								</div>
								
								
						
								<div class="col-12">
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
									<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
								</div>
		
						</form:form>
  						<%-- FUNZIONE JQUERY UI PER AUTOCOMPLETE --%>
								<script>
									
									$('#tipoPermesso').change(function(){
										  if($(this).val() == 'MALATTIA'){
										    $("#codiceCertificato").removeClass('d-none');
										    $("#attachment").removeClass('d-none');
										  }
										  else{
											  $("#codiceCertificato").addClass('d-none');
											  $("#attachment").addClass('d-none');
										  }
									});
									
									$('#giornoSingolo').change(function(){
										
										$("#dataFine").attr('disabled', this.checked)
									});
									
							
								</script>
				    
				    
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