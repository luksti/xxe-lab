<template>
  <div class="container h-100">
    <br><br><br>
    <h2>Introduction</h2>
    <br>
    <p>
      The goal of this lab is to introduce the widely spread security issue - XML external entity (XXE) for an IT student or a software developer, who is keen on developing and learning new things.
      In this introduction part let's have a first glance of this security issue.</p>
    <br>
    <p>
      XXE security risk is widely spread, because it can become a vulnerability whenever XML parsing takes place.
      For example services that change information via XML (SOAP) or the simpliest example would be an xml file upload.
      Being vulnerable to XXE can result in losing sensitive data, do port scanning and in worst case scenario - Denial of Service.
    </p>
    <br>
    <p>
      Having an XXE vulnerability is all about Document Type Definitions (DTDs). By declaring DTDs and using them in the xml data makes it possible to execute remote code in the DTD.
      Simple example of reading the etc/passwd file content:
    </p>
    <div style="font-family:'Droid Sans Mono'; background-color: #f9f9f9; max-width:430px;margin:0 auto; font-size:10px">
    <p>
      &lt;!DOCTYPE foo [</p><p>
    &lt;!ELEMENT foo ANY&gt;</p><p>
      &lt;!ENTITY xxe SYSTEM &quot;file:///etc/passwd&quot;&gt;]&gt;</p>
    <p>
      &lt;foo&gt;&amp;xxe;&lt;/foo&gt;
    </p>
    </div>
    <p>
      According to <a style="color:black; text-decoration:underline" href="https://www.owasp.org/images/7/72/OWASP_Top_10-2017_%28en%29.pdf.pdf">OWASP</a> the easiest way of defending against XXE is to disallow DTD processing in XML parser.
      One can disable DTDs completely or disable only external DTDs, general entities or general parameters. Here is an example how to configure XPathExpression against XXE:
    </p>
    <div style="font-family:'Droid Sans Mono'; background-color: #f9f9f9; max-width:430px;margin:0 auto; font-size:10px">
      <p>
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        <b>df.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
          df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");</b>
        DocumentBuilder builder = df.newDocumentBuilder();
        String result = new XPathExpression().evaluate( builder.parse(
        new ByteArrayInputStream(xml.getBytes())) );;
      </p>
    </div>


  </div>
</template>

<script>
export default {}
</script>

<style scoped>

</style>
