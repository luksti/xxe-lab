<template>
  <div class="container h-100">
    <br><br><br>
    <h2>XXE-attack</h2>
    <p>In the current lesson you would need to create an xml that would demonstrate the XXE vulnerability. Study the structure from browser development tools or from backend code. On a successful attack you will see the executed code outcome from the table below. NB! use hints and solution only when really neccessary!</p>


    <div style="width:300px; float:right; margin:0 auto;">
      <b-button v-b-tooltip.click title="Take the example xml from the project source files 'People.xml' and modify according to your goal" style="float:right" variant="light">Hint 1</b-button>
      <br><br>
      <b-button style="float:right" v-b-tooltip.click title="Declare a doctype entity where the remote code is executed and use it in one of the variables" variant="light">Hint 2</b-button>
      <br><br>
      <b-button style="float:right" v-b-tooltip.click title="One of the solutions can be found in people-xxe.xml file" variant="light">Solution</b-button>
      <br><br>
    <b-form-file
      accept=".xml"
      v-model="file"
      :state="Boolean(file)"
      placeholder="Choose a file..."
      drop-placeholder="Drop file here..."
    />
      <br><br>
      <b-button v-if="file" v-on:click="addPersion()" style="width:80px; float: right" variant="dark">Upload</b-button>
      <b-button v-else disabled style="width:80px; float: right" variant="dark">Upload</b-button>
      <br><br>
    </div>
  <!--<div class="mt-3">Selected file: {{ file ? file.name : '' }}</div>-->


    <br><br><br><br><br>
    <!--<h2>Persons</h2>-->
    <!--<li v-for="person in people">{{person.name}} - {{person.address}} - {{person.comment}}</li>-->
    <b-table class="table" :items="people"/>
  </div>


</template>

<script>
  import axios from "axios";

  export default {
    name: 'lesson1',
    data() {
      return {
        people: [],
        file: null
      }
    },
    mounted() {
      axios({method: 'GET', url: 'http://127.0.0.1:9020/xxe/lesson1/people'})
        .then(result => {
          this.people = result.data.data;
        }, error => {
          console.error(error)
        })
    },

    methods:
      {
        getPersons() {
          axios({method: 'GET', url: 'http://127.0.0.1:9020/xxe/lesson1/people'})
            .then(result => {
              this.people = result.data.data;
            }, error => {
              console.error(error)
            })
        },
        addPersion() {
          let formData = new FormData();
          formData.append('file', this.file);
          axios({ method: "POST", "url": "http://127.0.0.1:9020/xxe/lesson1/create", "data": formData, "headers": { "content-type": "multipart/form-data" } }).then(result => {
          }, error => {
            console.error(error);
          }).then(this.getPersons);
        }
      }
  }
</script>

<style scoped>
</style>
