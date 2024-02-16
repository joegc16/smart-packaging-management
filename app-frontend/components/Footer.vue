<template>
  <div class="footer-basic">
    <footer>
      <ul class="list-inline">
        <li class="list-inline-item"><a href="#">Home</a></li>
        <li class="list-inline-item"><a href="#">Services</a></li>
        <li class="list-inline-item"><a href="#">About</a></li>
        <li class="list-inline-item"><a href="#">Terms</a></li>
        <li class="list-inline-item"><a href="#">Privacy Policy</a></li>
      </ul>
      <p class="copyright">Company Name © 2024</p>
    </footer>
  </div>
</template>

<style>
.footer-basic {
  position: fixed;
  bottom: 0;
  width: 100%;
  background-color: #f0f0f0;
  color: #4b4c4d;
  padding: 10px 0;
  text-align: center;
}

.footer-basic ul {
  padding: 0;
  list-style: none;
}

.footer-basic ul li {
  display: inline;
  margin: 0 10px;
}

.footer-basic ul li a {
  color: inherit;
  text-decoration: none;
}

.footer-basic ul li a:hover {
  opacity: 0.8;
}

.footer-basic .copyright {
  margin-top: 10px;
  font-size: 12px;
}
</style>
