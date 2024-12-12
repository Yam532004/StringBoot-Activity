<template>
  <div class="search">
    <b-form @submit.prevent="onSubmit">
      <b-form-group label-for="input-word">
        <h2><b>Từ Điển</b></h2>
        <div style="display: flex;">
          <b-form-input id="input-word" type="text" placeholder="" v-model="searchWord" required></b-form-input>
          <b-button type="submit" variant="primary">Search</b-button>
        </div>
      </b-form-group>
    </b-form>
    <div v-if="result">
      <h3 style="color: #5AAC99;">{{ result }}</h3>
    </div>
  </div>
</template>

<script>

import {
  defineComponent, ref
} from 'vue';
import axios from 'axios';
import { BForm, BFormGroup, BFormInput, BButton } from 'bootstrap-vue-3';

export default defineComponent({
  components: {
    BForm,
    BFormGroup,
    BFormInput,
    BButton,
  },
  setup() {
    const searchWord = ref('');
    const result = ref(null);

    const onSubmit = async () => {
      if (!searchWord.value) {
        result.value = 'Vui lòng nhập từ cần tìm.';
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8080/dictionary?word=${searchWord.value}`);
        console.log(response.data);
        result.value = response.data; // Điều chỉnh theo cấu trúc dữ liệu trả về
      } catch (error) {
        if(error.status === 404 ) {
          result.value = "Không tìm thấy từ này";
        }
      }
    };
    return {
      searchWord,
      result,
      onSubmit
    }
  }
})
</script>
