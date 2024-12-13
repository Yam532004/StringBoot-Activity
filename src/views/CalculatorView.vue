<template>
  <b-container fluid>
    <b-row class="my-1">
      <b-col sm="3">
        <label for="firstNumber">First Number:</label>
      </b-col>
      <b-col sm="9">
        <b-form-input
          id="firstNumber"
          v-model="firstNumber"
          type="number"
          placeholder="Enter the first number"
        ></b-form-input>
      </b-col>
    </b-row>

    <b-row class="my-1">
      <b-col sm="3">
        <label for="secondNumber">Second Number:</label>
      </b-col>
      <b-col sm="9">
        <b-form-input
          id="secondNumber"
          v-model="secondNumber"
          type="number"
          placeholder="Enter the second number"
        ></b-form-input>
      </b-col>
    </b-row>

    <b-row class="my-1">
      <b-col sm="3">
        <label for="operation">Operation:</label>
      </b-col>
      <b-col sm="9">
        <b-form-select
          v-model="operation"
          :options="operationOptions"
          id="operation"
        ></b-form-select>
      </b-col>
    </b-row>

    <b-row class="my-1">
      <b-col sm="12">
        <b-button @click="calculate" variant="primary">Calculate</b-button>
      </b-col>
    </b-row>

    <b-row class="my-1" v-if="result !== null">
      <b-col sm="12">
        <h4>Result: {{ result }}</h4>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { defineComponent, ref } from 'vue';
import { BFormInput, BFormSelect, BButton, BContainer, BRow, BCol } from 'bootstrap-vue-3';
import axios from 'axios'; // Import axios để gọi API

export default defineComponent({
  components: {
    BFormInput,
    BFormSelect,
    BButton,
    BContainer,
    BRow,
    BCol,
  },
  setup() {
    const firstNumber = ref(0);
    const secondNumber = ref(0);
    const operation = ref('+');
    const result = ref(null);

    const operationOptions = [
      { value: '+', text: 'Addition' },
      { value: '-', text: 'Subtraction' },
      { value: '*', text: 'Multiplication' },
      { value: '/', text: 'Division' },
    ];

    const calculate = async () => {
      if (isNaN(firstNumber.value) || isNaN(secondNumber.value)) {
        alert('Please enter valid numbers');
        return;
      }

      try {
        // Gọi API backend với các tham số đã nhập
        const response = await axios.get('http://localhost:8080/calculator', {
          params: {
            firstNumber: firstNumber.value,
            secondNumber: secondNumber.value,
            operator: operation.value,
          },
        });

        // Lấy kết quả từ API và cập nhật trong giao diện
        result.value = response.data;
      } catch (error) {
        console.error('Error calling the API:', error);
        alert('An error occurred while calculating. Please try again.');
        result.value = null;
      }
    };

    return {
      firstNumber,
      secondNumber,
      operation,
      result,
      operationOptions,
      calculate,
    };
  },
});
</script>
