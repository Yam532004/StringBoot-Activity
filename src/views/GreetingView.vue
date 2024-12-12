<template>
  <div>
    <h1>{{ greeting }}</h1>
  </div>
</template>

<script>
export default {
  data() {
    return {
      greeting: 'Loading...', // Giá trị mặc định
    };
  },
  async mounted() {
    try {
      const name = this.$route.query.name || ""; // Giá trị mặc định nếu không có tham số
      const response = await fetch(`http://localhost:8080/greeting?name=${name}`);
      console.log(response.data)
      this.greeting = await response.text(); // Lấy dữ liệu từ API và gán vào greeting
    } catch (error) {
      this.greeting = error.message;
    }
  },
};
</script>

<style scoped>
h1 {
  color: blue;
}
</style>
