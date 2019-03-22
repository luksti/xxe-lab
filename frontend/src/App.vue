<template>
  <div id="app">
    <h1 style="color:gray">XXE lab</h1>
    <div id="navbar">
      <b-nav tabs justified class="nav justify-content-center">
        <b-nav-item to="/">Introduction</b-nav-item>
        <b-nav-item to="lesson1">Lesson 1 - XXE-attack</b-nav-item>
        <b-nav-item to="lesson2">Lesson 2 - JAXB defence</b-nav-item>
        <b-nav-item to="lesson3">Lesson 3 - SAXReader defence</b-nav-item>
        <b-nav-item to="lesson4">Lesson 4 - XMLReader defence</b-nav-item>
        <b-nav-item to="lesson5">Lesson 5 - XMLInputFactory defence</b-nav-item>
      </b-nav>
    </div>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.nav-link {
  color:black!important
}
</style>
