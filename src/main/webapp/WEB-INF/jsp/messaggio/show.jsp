<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<!-- Common imports in pages -->
	<jsp:include page="../header.jsp" />
	<title>Visualizza Messaggio</title>
	
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  	<div class="container">
			
			<div class='card'>
			    <div class='card-header'>
			        Visualizza dettaglio messaggio
			    </div>
			
			    <div class='card-body'>
			    	<dl class="row">
						<dt class="col-sm-3 text-right">Oggetto:</dt>
						<dd class="col-sm-9">${show_messaggio_attr.oggetto}</dd>
			    	</dl>
			    	<dl class="row">
						<dt class="col-sm-3 text-right">Testo:</dt>
						<dd class="col-sm-9">${show_messaggio_attr.testo}</dd>
			    	</dl>
			    	
			    	
			    <!-- end card body -->
			    </div>
			    
			    <div class='card-footer'>
			        <a href="${pageContext.request.contextPath }/messaggio/" class='btn btn-outline-secondary' style='width:85px'>
			            <i class='fa fa-chevron-left'></i> Indietro
			        </a>
			        <a href="${pageContext.request.contextPath }/richiestapermesso/show/${show_messaggio_attr.richiestaPermesso.id}" class='btn btn-outline-success' style='width:147px'>
			            <i class='fa fa-chevron-left'></i> Vai alla Richiesta
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