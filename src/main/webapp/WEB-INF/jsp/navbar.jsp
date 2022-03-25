<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
.btn-custom:hover{
	outline: none;
	background: none;
	color: #c6dbff;
}

.btn-custom:focus{
	outline: none;
	background: none;
	box-shadow: none;
}
.btn-custom{
	border: none;
}

</style>
<header>
  <!-- Fixed navbar -->
 <nav class="navbar navbar-expand-lg navbar-dark bg-primary" aria-label="Eighth navbar example">
    <div class="container">
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample07" aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExample07">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link ${path == 'home'?'active':''}" aria-current="page" href="${pageContext.request.contextPath}/home">Home</a>
          </li>

	<!-- 
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown07" data-bs-toggle="dropdown" aria-expanded="false">Dropdown</a>
            <ul class="dropdown-menu" aria-labelledby="dropdown07">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/regista/search">Ricerca Registi</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/regista/insert">Inserisci Regista</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/film/search">Ricerca Film</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/film/insert">Inserisci Film</a></li>
            </ul> 
          </li>
           -->
          <sec:authorize access="hasAnyRole('ADMIN', 'BO_USER')">
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle ${path == 'gestioneDipendenti'?'active':''}" href="#" id="dropdown01" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Gestione Dipendenti</a>
		        <div class="dropdown-menu" aria-labelledby="dropdown01">
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/dipendente/search">Ricerca Dipendenti</a>
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/dipendente/insert">Inserisci Dipendente</a>
		        </div>
		      </li>
		   </sec:authorize>
           <sec:authorize access="hasRole('ADMIN')">
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle ${path == 'gestioneUtenze'?'active':''}" href="#" id="dropdown02" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Gestione Utenze</a>
		        <div class="dropdown-menu" aria-labelledby="dropdown01">
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/utente/search">Ricerca Utenti</a>
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/utente/insert">Inserisci Utente</a>
		        </div>
		      </li>
		   </sec:authorize>
		   <sec:authorize access="hasRole('BO_USER')">
		    <li class="nav-item">
            	<a class="nav-link ${path == 'ricercaPermessi'?'active':''}" aria-current="page" href="${pageContext.request.contextPath}/richiestapermesso/search">Ricerca Permessi</a>
          	</li>
		   </sec:authorize>
		   <sec:authorize access="hasRole('BO_USER')">
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle ${path == 'gestioneMessaggi'?'active':''}" href="#" id="dropdown04" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Gestione Messaggi</a>
		        <div class="dropdown-menu" aria-labelledby="dropdown01">
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/utente/search">Ricerca Utenti</a>
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/utente/insert">Inserisci Utente</a>
		        </div>
		      </li>
		   </sec:authorize>
        </ul>
      </div>
      
      <sec:authorize access="isAuthenticated()">
      <sec:authentication var="principal" property="principal" />
        <div class="dropdown">
        <button class="btn dropdown-toggle btn-primary btn-custom" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
          Utente: <sec:authentication property="name"/> (${userInfo.nome } ${userInfo.cognome })
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/utente/changepassword/${principal.username}">Reset Password</a></li>
        </ul>
    </div>
      </sec:authorize>
      
      <sec:authorize access="!isAuthenticated()">
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
      </sec:authorize>
    </div>
  </nav>
  
  
</header>
