<template>
  <div v-if="user" class="user-details">
    <div class="user-card">
      <div class="user-info">
        <h1>{{ user.name }} Details</h1>
        <p><strong>Name:</strong> {{ user.name }}</p>
        <p><strong>Username:</strong> {{ user.username }}</p>
        <p><strong>Email:</strong> {{ user.email }}</p>
        <p><strong>Role:</strong> {{ role }}</p>
        <button v-if="!editing" @click="toggleEdit" class="edit-button">
          Edit
        </button>
        <button v-if="!editing" @click="confirmDelete" class="delete-button">
          Delete User
        </button>
        <div v-if="showConfirmationDialog" class="confirmation-dialog">
          <p>Are you sure you want to delete your account?</p>
          <button @click="deleteCustomer">Yes, Delete</button>
          <button @click="cancelDelete">Cancel</button>
        </div>
        <div v-if="editing" class="edit-fields">
          <p>Name</p>
          <input
            type="text"
            v-model="editedUser.name"
            placeholder="Name"
            class="edit-input"
          />
          <p>Username</p>
          <input
            type="text"
            v-model="editedUser.username"
            placeholder="Username"
            class="edit-input"
          />
          <p>Password</p>
          <input
            type="text"
            v-model="editedUser.password"
            placeholder="Password"
            class="edit-input"
          />
          <p>Email</p>
          <input
            type="text"
            v-model="editedUser.email"
            placeholder="Email"
            class="edit-input"
          />
          <button @click="saveChanges" class="save-button">Save Changes</button>
          <button @click="cancelEdit" class="cancel-button">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  user: Object,
});
const emit = defineEmits(["saveChanges", "cancelEdit", "deleteCustomer"]);

import { ref } from "vue";

const editing = ref(false);
const editedUser = ref({ ...props.user });
const role = computed(() => {
  if (props.user.role == 1) {
    return "Manufacturer";
  } else if (props.user.role == 2) {
    return "Customer";
  } else if (props.user.role == 3) {
    return "LogisticsOperator";
  } else {
    return "Inrecognizable role";
  }
});
const showConfirmationDialog = ref(false);

const toggleEdit = () => {
  editing.value = !editing.value;
};

const saveChanges = () => {
  if (editedUser.value.password == null || editedUser.value.password == "") {
    alert("Password has to be filled in");
    return;
  }
  const userToSave = editedUser.value;
  emit("saveChanges", userToSave);
  toggleEdit();
};

const cancelEdit = () => {
  emit("cancelEdit", editedUser);
  toggleEdit();
};

const confirmDelete = () => {
  showConfirmationDialog.value = true;
};

const cancelDelete = () => {
  showConfirmationDialog.value = false;
};
const deleteCustomer = () => {
  emit("deleteCustomer");
  showConfirmationDialog.value = false;
};
</script>

<style scoped>
.user-details {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f0f0;
}

.user-card {
  width: 400px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.user-avatar img {
  width: 100%;
  height: auto;
}

.user-info {
  padding: 20px;
}

h1 {
  margin-top: 0;
  font-size: 28px;
  color: #333;
}

p {
  margin: 10px 0;
  font-size: 18px;
  color: #555;
}

strong {
  font-weight: bold;
  color: #333;
}

.edit-button,
.save-button,
.cancel-button {
  background-color: #4caf50; /* Green */
  border: none;
  color: white;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 5px;
  transition-duration: 0.4s;
}

.delete-button {
  background-color: #ff4800; /* Green */
  border: none;
  color: white;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 5px;
  transition-duration: 0.4s;
}

.delete-button:hover {
  background-color: #db864d; /* Darker Green */
}

.edit-button:hover,
.save-button:hover,
.cancel-button:hover {
  background-color: #45a049; /* Darker Green */
}

.edit-fields {
  display: flex;
  flex-direction: column;
}

.edit-input {
  margin-bottom: 10px;
  padding: 8px;
  border-radius: 5px;
  border: 1px solid #ccc;
  font-size: 16px;
}
</style>
