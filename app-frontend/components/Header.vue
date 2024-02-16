<template>
    <div>
        <header>
            <h5>OnShop</h5>
            <nav>
            <a href="/">Home</a>
            <a href="#">About</a>
            <a href="#">Services</a>
            <a href="#">Contact</a>
            <a href="">Login</a>
              <a href="">Logout</a>
            </nav>
        </header>
    </div>
</template>
<script setup>

</script>
<style>

header {
  background-color: #333; /* Dark background color */
  color: #fff; /* Text color */
  padding: 10px; /* Padding for content inside the header */
  display: flex;
  position: relative;
  line-height: normal;
  justify-content: space-between; /* Space items evenly along the main axis */
  align-items: center; /* Center items vertically */
}

h1 {
  margin: 0; /* Remove default margin for h1 */
  justify-content: flex-start; /* Align to the left */
}

nav {
  text-align: right;
  justify-content: center; /* Center items horizontally */
  align-items: center; /* Center items vertically */
}

nav a {
  color: #fff; /* Text color for buttons */
  text-decoration: none;
  padding: 8px 12px; /* Padding for each button */
  border-radius: 5px; /* Rounded corners for buttons */
  background-color: #333; /* Button background color */
  transition: background-color 0.3s ease; /* Smooth transition effect */
}

nav a:hover {
  background-color: #777; /* Lighter color on hover */
  color: #fff; /* Text color on hover */
  text-decoration: none; /* Remove underline on hover */
}

</style>