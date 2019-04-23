<template>
  <div class="container h-100">
    <br><br><br>
    <h2>JAXB XML parser defence</h2>
    <p>In the current lesson you would need to study the JAXB parser code and configure it to remove XXE vulnerability. After you have configured the xml parser, restart backend-api and check if your solution was correct. NB! use hints only when really neccessary!</p>


    <div style="width:300px; float:right; margin:0 auto;">
      <b-button v-b-tooltip.click title="JAXB parser method for current lesson can be found in Lesson2Service.java" style="float:right" variant="light">Hint 1</b-button>
      <br><br>
      <b-button style="float:right" v-b-tooltip.click title="XXE vulnerability can be removed by setting SaxParserFactory features like 'http://xml.org/sax/features/external-general-entities', 'http://xml.org/sax/features/external-parameter-entities' and 'http://apache.org/xml/features/nonvalidating/load-external-dtd' to false" variant="light">Hint 2</b-button>
      <br><br>

      <br><br>
      <b-button v-on:click="getSolution()" style="width:200px; float: right" variant="dark">Check for solution</b-button>
      <br><br><br>
      <div >
        <b-alert :show="solution" variant="success">
          Your solution is correct, good job!
        </b-alert>
        <b-alert :show="!solution" variant="danger">
          Solution is incorrect!
        </b-alert>
      </div>
    </div>

  </div>
</template>

<script>
  import axios from "axios";

  export default {
    name: 'lesson2',
    data() {
      return {
        solution: false
      }
    },
    mounted() {
      this.getSolution()
    },

    methods:
      {
        getSolution() {
          axios({method: 'GET', url: 'http://192.168.6.2/xxe/lesson2/check'})
            .then(result => {
              this.solution = result.data.ok;
            }, error => {
              console.error(error)
            }).then(this.solution = false)
        }
      }
  }
</script>

<style scoped>

</style>
