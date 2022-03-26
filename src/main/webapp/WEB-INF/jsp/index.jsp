<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="./header.jsp" />
		<!-- Custom styles per le features di bootstrap 'Columns with icons' -->
	   <link href="${pageContext.request.contextPath}/assets/css/features.css" rel="stylesheet">
	   
	   <title>Gestione Permessi</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   		
	   		<!-- #####################################  -->
	   		<!-- elementi grafici per le features in basso  -->
	   		<!-- #####################################  -->
	   		<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
			  <symbol id="people-circle" viewBox="0 0 16 16">
			    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
			    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
			  </symbol>
			  <symbol id="collection" viewBox="0 0 16 16">
			    <path d="M2.5 3.5a.5.5 0 0 1 0-1h11a.5.5 0 0 1 0 1h-11zm2-2a.5.5 0 0 1 0-1h7a.5.5 0 0 1 0 1h-7zM0 13a1.5 1.5 0 0 0 1.5 1.5h13A1.5 1.5 0 0 0 16 13V6a1.5 1.5 0 0 0-1.5-1.5h-13A1.5 1.5 0 0 0 0 6v7zm1.5.5A.5.5 0 0 1 1 13V6a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-.5.5h-13z"/>
			  </symbol>
			  <symbol id="toggles2" viewBox="0 0 16 16">
			    <path d="M9.465 10H12a2 2 0 1 1 0 4H9.465c.34-.588.535-1.271.535-2 0-.729-.195-1.412-.535-2z"/>
			    <path d="M6 15a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm0 1a4 4 0 1 1 0-8 4 4 0 0 1 0 8zm.535-10a3.975 3.975 0 0 1-.409-1H4a1 1 0 0 1 0-2h2.126c.091-.355.23-.69.41-1H4a2 2 0 1 0 0 4h2.535z"/>
			    <path d="M14 4a4 4 0 1 1-8 0 4 4 0 0 1 8 0z"/>
			  </symbol>
			  <symbol id="chevron-right" viewBox="0 0 16 16">
			    <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
			  </symbol>
			  
			  <symbol id="search" viewBox="0 0 16 16">
			  <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
			  </symbol>
			  <symbol id="people" viewBox="0 0 16 16">
			  <path d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1A.261.261 0 0 1 7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002a.274.274 0 0 1-.014.002H7.022zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10A5.493 5.493 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"/>
			  </symbol>
			</svg>
			<!-- ############## end ###################  -->
	   
	   
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="./navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  	<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
				  ${errorMessage}
				  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>
			    <sec:authorize access="!hasRole('ADMIN')">
			     <div class="p-5 mb-4 bg-light rounded-3">
				      <div class="container-fluid py-5">
				        <h1 class="display-5 fw-bold text-center">Benvenuto ${userInfo.nome}</h1>
				      </div>
			    </div>
			    </sec:authorize>
			  </div>
			  
			  <!--  features di bootstrap 'Columns with icons'  -->
			  <div class="container px-4 py-5" id="featured-3">
			   
			    <div class="row">
				  <sec:authorize access="hasAnyRole('ADMIN', 'BO_USER')">
				  <div class="col-sm-3 mx-auto">
				    <div class="card border-primary text-center">
				      <div class="card-body">
				        <h5 class="card-title">Gestione Dipendenti</h5>
				        <p class="card-text"><svg class="bi bi-search" width="40%" height="40%"><use xlink:href="#search"/></svg></p>
				        <a href="/dipendente/search" class="stretched-link"></a>
				      </div>
				    </div>
				  </div>
				  </sec:authorize>
				  
				  <sec:authorize access="hasRole('BO_USER')">
				  <div class="col-sm-3 mx-auto">
				    <div class="card border-primary text-center">
				      <div class="card-body">
				        <h5 class="card-title">Ricerca Permessi</h5>
				        <p class="card-text"><svg class="bi bi-search" width="40%" height="40%"><use xlink:href="#search"/></svg></p>
				        <a href="/richiestapermesso/search" class="stretched-link"></a>
				      </div>
				    </div>
				  </div>
				  </sec:authorize>
				  
				  <sec:authorize access="hasRole('BO_USER')">
				  <div class="col-sm-3 mx-auto">
				    <div class="card border-primary text-center">
				      <div class="card-body">
				        <h5 class="card-title">Gestione Messaggi</h5>
				        <p class="card-text"><svg class="bi bi-search" width="40%" height="40%"><use xlink:href="#search"/></svg></p>
				        <a href="/messaggio/search" class="stretched-link"></a>
				      </div>
				    </div>
				  </div>
				  </sec:authorize>
				  
				  <sec:authorize access="hasRole('ADMIN')">
				  <div class="col-sm-3 mx-auto">
				    <div class="card border-primary text-center">
				      <div class="card-body">
				        <h5 class="card-title">Gestione Utenze</h5>
				        <p class="card-text"><svg class="bi bi-search" width="40%" height="40%"><use xlink:href="#people"/></svg></p>
				        <a href="/utente/search" class="stretched-link"></a>
				      </div>
				    </div>
				  </div>
				  </sec:authorize>
				  
				  <sec:authorize access="hasRole('DIPENDENTE_USER')">
				  <div class="col-sm-3 mx-auto">
				    <div class="card border-primary text-center">
				      <div class="card-body">
				        <h5 class="card-title">Gestione Permessi</h5>
				        <p class="card-text"><svg class="bi bi-search" width="40%" height="40%"><use xlink:href="#people"/></svg></p>
				        <a href="/richiestapermesso/searchpersonal" class="stretched-link"></a>
				      </div>
				    </div>
				  </div>
				  </sec:authorize>
		
				  
				</div>
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="./footer.jsp" />
	  </body>
</html>