<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
<head>
	<jsp:include page="../header.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/jqueryUI/jquery-ui.min.css" />
	<title>Ricerca Richieste Permesso</title>
	
    
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp"></jsp:include>
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  <div class="container">
	
			<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': ''}" role="alert">
			  ${errorMessage}
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
			

			
			<div class='card'>
			    <div class='card-header'>
			        <h5>Ricerca Richieste Permesse</h5> 
			    </div>
			    <div class='card-body'>
	
						<form method="post" action="${pageContext.request.contextPath}/richiestapermesso/list" class="row g-3">
							<div class="col-md-2">
									<label for="tipoPermesso" class="form-label">Tipo Permesso </label>
								    <select class="form-select" id="tipoPermesso" name="tipoPermesso" >
								    	<option value="" selected> - Selezionare - </option>
								      	<option value="FERIE" >FERIE</option>
								      	<option value="MALATTIA"  >MALATTIA</option>
								    </select>
							</div>
							
							<div id="codiceCertificato" class="col-md-6 d-none">
								<label for="codiceCertificato" class="form-label">Codice Certificato</label>
								<input type="text" name="codiceCertificato" id="codiceCertificato" class="form-control" placeholder="Inserire il codice certificato" >
							</div>
							
							<div class="col-md-2">
								<label for="dataInizio" class="form-label">Data di Inizio</label>
                        		<input class="form-control" id="dataInizio" type="date" placeholder="dd/MM/yy"
                            		title="formato : gg/mm/aaaa"  name="dataInizio" >
							</div>
							<div class="col-md-2">
								<label for="dataFine" class="form-label">Data di Fine</label>
                        		<input class="form-control" id="dataFine" type="date" placeholder="dd/MM/yy"
                            		title="formato : gg/mm/aaaa"  name="dataFine" >
							</div>
							<div class="col-md-3">
								<label for="dipendenteSearchInput" class="form-label">Dipendente:</label>
									<input class="form-control ${status.error ? 'is-invalid' : ''}" type="text" id="dipendenteSearchInput"
										name="dipendenteSearchInput" value="${search_richiestaPermesso_attr.dipendente.nome}${empty search_richiestaPermesso_attr.dipendente.nome?'':' '}${search_richiestaPermesso_attr.dipendente.cognome}">
								<input type="hidden" name="dipendente.id" id="dipendenteId" value="${search_richiestaPermesso_attr.dipendente.id}">
							</div>
							
							
							<div class="col-md-3">
								<label for="approvato" class="form-label">Stato</label>
								    <select class="form-select " id="approvato" name="approvato" >
								    	<option value="" selected> - Selezionare - </option>
								    	<option value="true">Approvato</option>
								      	<option value="false" >Non approvato</option>
							    	</select>
							</div>
							
				
							<div class="col-12">	
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
							</div>
	
							
						</form>
						
						<%-- FUNZIONE JQUERY UI PER AUTOCOMPLETE --%>
								<script>
									$("#dipendenteSearchInput").autocomplete({
										 source: function(request, response) {
										        $.ajax({
										            url: "../dipendente/searchDipendentiAjax",
										            datatype: "json",
										            data: {
										                term: request.term,   
										            },
										            success: function(data) {
										                response($.map(data, function(item) {
										                    return {
											                    label: item.label,
											                    value: item.value
										                    }
										                }))
										            }
										        })
										    },
										//quando seleziono la voce nel campo deve valorizzarsi la descrizione
									    focus: function(event, ui) {
									        $("#dipendenteSearchInput").val(ui.item.label)
									        return false
									    },
									    minLength: 2,
									    //quando seleziono la voce nel campo hidden deve valorizzarsi l'id
									    select: function( event, ui ) {
									    	$('#dipendenteId').val(ui.item.value);
									    	//console.log($('#contribuenteId').val())
									        return false;
									    }
									});
									
									$('#tipoPermesso').change(function(){
										  if($(this).val() == 'MALATTIA'){
										    $('#myselectVolvo option:lt(2)').remove();
										    $("#codiceCertificato").removeClass('d-none');
										  }
										  else{
											  $("#codiceCertificato").addClass('d-none');
										  }
									});
									
							
								</script>
								<!-- end script autocomplete -->
			    
				<!-- end card-body -->			   
			    </div>
			</div>	
	
		</div>
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>