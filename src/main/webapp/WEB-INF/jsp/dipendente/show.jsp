<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<!-- Common imports in pages -->
	<jsp:include page="../header.jsp" />
	<title>Visualizza elemento</title>
	
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  	<div class="container">
			
			<div class='card'>
			    <div class='card-header'>
			        Visualizza dettaglio
			    </div>
			
			    <div class='card-body'>
			    	<dl class="row">
						<dt class="col-sm-3 text-right">Nome:</dt>
						<dd class="col-sm-9">${show_dipendente_attr.nome}</dd>
			    	</dl>
			    	<dl class="row">
						<dt class="col-sm-3 text-right">Cognome:</dt>
						<dd class="col-sm-9">${show_dipendente_attr.cognome}</dd>
			    	</dl>
			    	<dl class="row">
						<dt class="col-sm-3 text-right">Codice Fiscale:</dt>
						<dd class="col-sm-9">${show_dipendente_attr.codFis}</dd>
			    	</dl>
			    	<dl class="row">
						<dt class="col-sm-3 text-right">Email:</dt>
						<dd class="col-sm-9">${show_dipendente_attr.email}</dd>
			    	</dl>
			    	<dl class="row">
						<dt class="col-sm-3 text-right">Sesso:</dt>
						<dd class="col-sm-9">${show_dipendente_attr.sesso}</dd>
			    	</dl>

			    	
			    	<dl class="row">
						<dt class="col-sm-3 text-right">Data di Nascita:</dt>
						<dd class="col-sm-9"><fmt:formatDate type = "date" value = "${show_dipendente_attr.dataNascita}" /></dd>
			    	</dl>
			    	<dl class="row">
						<dt class="col-sm-3 text-right">Data Assunzione:</dt>
						<dd class="col-sm-9"><fmt:formatDate type = "date" value = "${show_dipendente_attr.dataAssunzione}" /></dd>
			    	</dl>
			    	<dl class="row">
						<dt class="col-sm-3 text-right">Data Dimissioni:</dt>
						<dd class="col-sm-9"><fmt:formatDate type = "date" value = "${show_dipendente_attr.dataDimissioni}" /></dd>
			    	</dl>
			    	
	
			    	
			    <!-- end card body -->
			    </div>
			    
			    <div class='card-footer'>
			        <a href="${pageContext.request.contextPath }/dipendente/" class='btn btn-outline-secondary' style='width:80px'>
			            <i class='fa fa-chevron-left'></i> Back
			        </a>
			    </div>
			<!-- end card -->
			</div>	
	
		<!-- end container -->  
		</div>
		
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>