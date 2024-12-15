<template>
  <div id="app" class="container mt-5">
    <h4 class="mb-4">Tìm kiếm nhân viên</h4>
    <form @submit.prevent="searchEmployees">
      <div class="row mb-3">
        <div class="col-md-4">
          <label for="name" class="form-label">Tên (Tìm kiếm gần đúng)</label>
          <input type="text" v-model="searchFilters.name" class="form-control" id="name" placeholder="Nhập tên">
        </div>
        <div class="col-md-4">
          <label for="dobFrom" class="form-label">Ngày sinh từ</label>
          <input type="date" v-model="searchFilters.dobFrom" class="form-control" id="dobFrom">
        </div>
        <div class="col-md-4">
          <label for="dobTo" class="form-label">Ngày sinh đến</label>
          <input type="date" v-model="searchFilters.dobTo" class="form-control" id="dobTo">
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-md-4">
          <label for="gender" class="form-label">Giới tính</label>
          <select v-model="searchFilters.gender" class="form-select" id="gender">
            <option value="">Tất cả</option>
            <option value="MALE">Nam</option>
            <option value="FEMALE">Nữ</option>
          </select>
        </div>
        <div class="col-md-4">
          <label for="salary" class="form-label">Mức lương</label>
          <select v-model="searchFilters.salaryRange" class="form-select" id="salary">
            <option value="">Tất cả</option>
            <option value="low">Dưới 10 triệu</option>
            <option value="medium">10-20 triệu</option>
            <option value="high">Trên 20 triệu</option>
          </select>
        </div>
        <div class="col-md-4">
          <label for="phone" class="form-label">Số điện thoại (Tìm kiếm gần đúng)</label>
          <input type="text" v-model="searchFilters.phone" class="form-control" id="phone" placeholder="Nhập số điện thoại">
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-md-4">
          <label for="department" class="form-label">Bộ phận</label>
          <select v-model="searchFilters.departmentId" class="form-select" id="department">
            <option value="">Tất cả</option>
            <option value="hr">Nhân sự</option>
            <option value="it">Công nghệ thông tin</option>
            <option value="sales">Kinh doanh</option>
          </select>
        </div>
      </div>

      <div class="d-flex gap-2">
        <button type="reset" @click="resetFilters" class="btn btn-secondary">Đặt lại</button>
        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
      </div>
    </form>
  </div>

  <div class="container mt-4">
    <h3 class="mb-4">Danh sách nhân viên</h3>
    <button class="btn btn-success mb-3" @click="showAddForm">+ Thêm Mới</button>
    <table class="table table-bordered">
      <thead>
        <tr>
          <th>STT</th>
          <th>Tên</th>
          <th>Ngày sinh</th>
          <th>Giới tính</th>
          <th>Lương</th>
          <th>SĐT</th>
          <th>Bộ phận</th>
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
          <td>{{ getDepartmentName(employee.departmentId) }}</td>
          <td>
            <button class="btn btn-primary btn-sm me-1" @click="showUpdateForm(employee)">Cập nhật</button>
            <button class="btn btn-danger btn-sm me-1" @click="deleteEmployee(employee.id)">Xóa</button>
            <button class="btn btn-info btn-sm" @click="showDetails(employee)">Chi tiết</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="showForm" class="modal show d-block" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ formMode === 'add' ? 'Thêm mới nhân viên' : 'Cập nhật nhân viên' }}</h5>
            <button type="button" class="btn-close" @click="closeForm"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="handleSubmit">
              <div class="mb-3">
                <label class="form-label">Tên</label>
                <input type="text" v-model="formData.name" class="form-control" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Ngày sinh</label>
                <input type="date" v-model="formData.dob" class="form-control" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Giới tính</label>
                <select v-model="formData.gender" class="form-select" required>
                  <option value="MALE">Nam</option>
                  <option value="FEMALE">Nữ</option>
                </select>
              </div>
              <div class="mb-3">
                <label class="form-label">Lương</label>
                <input type="number" v-model="formData.salary" class="form-control" required />
              </div>
              <div class="mb-3">
                <label class="form-label">SĐT</label>
                <input type="text" v-model="formData.phone" class="form-control" required />
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" @click="closeForm">Hủy</button>
                <button type="submit" class="btn btn-success">{{ formMode === 'add' ? 'Lưu' : 'Cập nhật' }}</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>

    <div v-if="showDetailModal" class="modal show d-block" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Thông tin chi tiết nhân viên</h5>
            <button type="button" class="btn-close" @click="closeDetailModal"></button>
          </div>
          <div class="modal-body">
            <p><strong>Tên:</strong> {{ selectedEmployee.name }}</p>
            <p><strong>Ngày sinh:</strong> {{ formatDate(selectedEmployee.dob) }}</p>
            <p><strong>Giới tính:</strong> {{ selectedEmployee.gender }}</p>
            <p><strong>Lương:</strong> {{ formatCurrency(selectedEmployee.salary) }}</p>
            <p><strong>SĐT:</strong> {{ selectedEmployee.phone }}</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="closeDetailModal">Đóng</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      employees: [],
      showForm: false,
      formMode: 'add', // 'add' or 'update'
      formData: {
        id: '',
        name: '',
        dob: '',
        gender: 'MALE',
        salary: 0,
        phone: ''
      },
      searchFilters: {
        name: "",
        dobFrom: "",
        dobTo: "",
        gender: "",
        salaryRange: "",
        phone: "",
        departmentId: "",
      },
      departments: [
      { id: 1, name: 'Nhân sự' },
      { id: 2, name: 'Công nghệ thông tin' },
      { id: 3, name: 'Kinh doanh' }
    ],
      showDetailModal: false,
      selectedEmployee: {},
    }
  },
  methods: {
     // Phương thức tìm tên bộ phận từ departmentId
     getDepartmentName(departmentId) {
      const department = this.departments.find(dep => dep.id === departmentId);
      return department ? department.name : 'Không xác định';
    },
    async fetchEmployees() {
      const response = await axios.get('http://localhost:8080/employees');
      this.employees = response.data;
    },
    async searchEmployees() {
      const response = await axios.get('http://localhost:8080/employees/search', {
        params: this.searchFilters
      });
      this.employees = response.data;
    },
    resetFilters() {
      this.searchFilters = {
        name: '',
        dobFrom: '',
        dobTo: '',
        gender: '',
        salaryRange: '',
        phone: '',
        departmentId: '',
      };
      this.fetchEmployees();
    },
    formatDate(date) {
      return new Date(date).toLocaleDateString('vi-VN');
    },
    formatCurrency(value) {
      return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
    },
    showAddForm() {
      this.formMode = 'add';
      this.formData = { id: '', name: '', dob: '', gender: 'MALE', salary: 0, phone: '' };
      this.showForm = true;
    },
    showUpdateForm(employee) {
      this.formMode = 'update';
      this.formData = { ...employee };
      this.showForm = true;
    },
    async handleSubmit() {
      if (this.formMode === 'add') {
        await axios.post('http://localhost:8080/employees', this.formData);
      } else if (this.formMode === 'update') {
        await axios.put(`http://localhost:8080/employees/${this.formData.id}`, this.formData);
      }
      this.closeForm();
      this.fetchEmployees();
    },
    async deleteEmployee(id) {
      if (confirm('Bạn có chắc chắn muốn xóa nhân viên này không?')) {
        await axios.delete(`http://localhost:8080/employees/${id}`);
        this.fetchEmployees();
      }
    },
    showDetails(employee) {
      this.selectedEmployee = employee;
      this.showDetailModal = true;
    },
    closeDetailModal() {
      this.showDetailModal = false;
    },
    closeForm() {
      this.showForm = false;
    }
  },
  mounted() {
    this.fetchEmployees();
  },
}
</script>
