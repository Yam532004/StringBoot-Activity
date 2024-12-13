<template>
  <div class="container mt-4">
    <h3 class="mb-4">Danh sách nhân viên</h3>
    <button class="btn btn-success mb-3" @click="openAddModal">+ Thêm Mới</button>
    <table class="table table-bordered">
      <thead>
        <tr>
          <th>STT</th>
          <th>Tên</th>
          <th>Ngày sinh</th>
          <th>Giới tính</th>
          <th>Lương</th>
          <th>SĐT</th>
          <th>Thao tác</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(employee, index) in employees" :key="employee.id">
          <td>{{ index + 1 }}</td>
          <td>{{ employee.name }}</td>
          <td>{{ formatDate(employee.dob) }}</td>
          <td>{{ employee.gender }}</td>
          <td>{{ formatCurrency(employee.salary) }}</td>
          <td>{{ employee.phone }}</td>
          <td>
            <button class="btn btn-primary btn-sm me-1" @click="editEmployee(employee)">Cập nhật</button>
            <button class="btn btn-danger btn-sm me-1" @click="deleteEmployee(employee.id)">Xóa</button>
            <button class="btn btn-info btn-sm" @click="viewDetails(employee.id)">Chi tiết</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Modal Thêm Mới -->
    <div v-if="showAddModal || showEditModal" class="modal show d-block" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ showAddModal ? "Thêm mới nhân viên" : "Cập nhật nhân viên" }}</h5>
            <button type="button" class="btn-close" @click="resetAndCloseModal"></button>
          </div>
          <div class="modal-body">
            <form>
              <div class="mb-3">
                <label class="form-label">Tên</label>
                <input type="text" v-model="form.name" class="form-control" />
              </div>
              <div class="mb-3">
                <label class="form-label">Ngày sinh</label>
                <input type="date" v-model="form.dob" class="form-control" />
              </div>
              <div class="mb-3">
                <label class="form-label">Giới tính</label>
                <select v-model="form.gender" class="form-select">
                  <option value="MALE">Nam</option>
                  <option value="FEMALE">Nữ</option>
                </select>
              </div>
              <div class="mb-3">
                <label class="form-label">Lương</label>
                <input type="number" v-model="form.salary" class="form-control" />
              </div>
              <div class="mb-3">
                <label class="form-label">SĐT</label>
                <input type="number" v-model="form.phone" class="form-control" />
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="resetAndCloseModal">Hủy</button>
            <button type="button" class="btn btn-success" @click="submitForm">
              {{ showAddModal ? "Lưu" : "Cập nhật" }}
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal Xem Chi Tiết -->
    <div v-if="showDetailsModal" class="modal show d-block" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Thông tin chi tiết nhân viên</h5>
            <button type="button" class="btn-close" @click="showDetailsModal = false"></button>
          </div>
          <div class="modal-body">
            <p><strong>Tên:</strong> {{ selectedEmployee.name }}</p>
            <p><strong>Ngày sinh:</strong> {{ formatDate(selectedEmployee.dob) }}</p>
            <p><strong>Giới tính:</strong> {{ selectedEmployee.gender }}</p>
            <p><strong>Lương:</strong> {{ formatCurrency(selectedEmployee.salary) }}</p>
            <p><strong>SĐT:</strong> {{ selectedEmployee.phone }}</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="showDetailsModal = false">Đóng</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import axios from "axios";

export default {
  setup() {
    const employees = ref([]);
    const showAddModal = ref(false);
    const showEditModal = ref(false);
    const showDetailsModal = ref(false);
    const selectedEmployee = ref({});
    const form = ref({ name: "", dob: "", gender: "", salary: 0, phone: "" });
    const apiBaseUrl = `http://localhost:8080/employees`;

    const fetchEmployees = async () => {
      try {
        const response = await axios.get(apiBaseUrl);
        employees.value = response.data;
      } catch (error) {
        console.error("Error fetching employees:", error);
      }
    };

    const formatDate = (dateString) => {
      if (!dateString) return "";
      return new Date(dateString).toLocaleDateString("vi-VN", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
      });
    };

    const formatCurrency = (amount) => {
      if (isNaN(amount)) return "0 VND";
      return new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(amount);
    };

    const openAddModal = () => {
      resetForm();
      showAddModal.value = true;
    };

    const resetForm = () => {
      form.value = { name: "", dob: "", gender: "", salary: 0, phone: "" };
    };

    const resetAndCloseModal = () => {
      resetForm();
      showAddModal.value = false;
      showEditModal.value = false;
    };

    const submitForm = async () => {
      try {
        if (showAddModal.value) {
          const response = await axios.post(apiBaseUrl, form.value);
          employees.value.push(response.data);
        } else if (showEditModal.value) {
          const response = await axios.put(`${apiBaseUrl}/${form.value.id}`, form.value);
          const index = employees.value.findIndex((e) => e.id === form.value.id);
          if (index !== -1) employees.value[index] = response.data;
        }
        resetAndCloseModal();
      } catch (error) {
        console.error("Error saving employee:", error);
      }
    };

    const editEmployee = (employee) => {
      form.value = { ...employee };
      showEditModal.value = true;
    };

    const deleteEmployee = async (id) => {
      const confirmDelete = confirm("Bạn có chắc chắn muốn xóa nhân viên này?");
      if (confirmDelete) {
        try {
          await axios.delete(`${apiBaseUrl}/${id}`);
          employees.value = employees.value.filter((e) => e.id !== id);
        } catch (error) {
          console.error("Error deleting employee:", error);
        }
      }
    };

    const viewDetails = (id) => {
      const employee = employees.value.find((e) => e.id === id);
      if (employee) {
        selectedEmployee.value = { ...employee };
        showDetailsModal.value = true;
      }
    };

    fetchEmployees();

    return {
      employees,
      form,
      selectedEmployee,
      showAddModal,
      showEditModal,
      showDetailsModal,
      fetchEmployees,
      resetForm,
      resetAndCloseModal,
      submitForm,
      editEmployee,
      deleteEmployee,
      formatDate,
      formatCurrency,
      viewDetails,
      openAddModal,
    };
  },
};
</script>
