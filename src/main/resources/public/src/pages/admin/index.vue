<template>
  <RouterLink to="/admin/orders">
    <p style="font-weight: 600; color: cornflowerblue">View orders</p>
  </RouterLink>
  <table>
    <tr>
      <th>NAME</th>
      <th>COLOR</th>
      <th>MANUFACTURER</th>
      <th>PRICE</th>
      <th>IMAGE</th>
      <th>QUANTITY</th>
      <th>ACTIONS</th>
    </tr>
    <tr v-for="p in products" :key="p.id">
      <td>{{ p.name }}</td>
      <td>{{ p.color }}</td>
      <td>{{ p.manufacturer }}</td>
      <td>{{ p.price }}</td>
      <td><img :src="p.image" :alt="p.name" style="width: 100px; height: 100px" /></td>
      <td>{{ p.quantity }}</td>
      <td>
        <button @click="product = p">Edit</button>
        <button @click="deleteProduct(p.id)" style="margin-left: 20px">Delete</button>
      </td>
    </tr>
  </table>
  <form @submit.prevent="saveProduct">
    <p>Product:</p>
    <input v-model="product.name" placeholder="Product name" type="text" />
    <select v-model="product.color">
      <option value="BLACK">Black</option>
      <option value="WHITE">White</option>
      <option value="RED">Red</option>
      <option value="GREEN">Green</option>
      <option value="BLUE">Blue</option>
    </select>
    <input v-model="product.manufacturer" placeholder="Product manufacturer" type="text" />
    <input v-model="product.price" placeholder="Product price" type="number" />
    <input type="file" @change="uploadImage" />
    <input v-model="product.quantity" placeholder="Product quantity" type="number" />
    <textarea v-model="product.description" placeholder="Product description"></textarea>
    <button type="submit">Save</button>
  </form>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { Product } from '@/types'

const products = ref<Product[]>([])

const product = ref<Product>({
  id: -1,
  name: '',
  color: 'BLACK',
  manufacturer: '',
  price: 0,
  image: '',
  quantity: 0,
  description: ''
})

const deleteProduct = (id: number) => {
  fetch(`http://localhost:8080/deleteProduct`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ id })
  }).then(() => {
    products.value = products.value.filter((product) => product.id !== id)
  })
}

const fileToBase64 = (inputFile: Blob) => {
  const promise = new Promise<string>(function (resolve, reject) {
    const reader = new FileReader()
    reader.onload = (event) => {
      if (!event.target) {
        return reject(new Error('Failed to load file'))
      }
      resolve(event.target.result as string)
    }
    reader.readAsDataURL(inputFile)
  })
  return promise
}

const uploadImage = async (event: Event) => {
  const target = event.target as HTMLInputElement
  if (!target.files || !target.files[0]) {
    return
  }
  const base64 = await fileToBase64(target.files[0])
  product.value.image = base64
}

const saveProduct = async () => {
  if (product.value.id === -1) {
    const res = await fetch('http://localhost:8080/createProduct', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(product.value)
    })
    const data = await res.json()
    products.value.push(data)
  } else {
    await fetch('http://localhost:8080/updateProduct', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(product.value)
    })
    const index = products.value.findIndex((p) => p.id === product.value.id)
    products.value[index] = product.value
  }
  product.value = {
    id: -1,
    name: '',
    color: 'BLACK',
    manufacturer: '',
    price: 0,
    image: '',
    quantity: 0,
    description: ''
  }
}

const refreshProducts = () => {
  fetch('http://localhost:8080/getProducts')
    .then((res) => res.json())
    .then((data) => (products.value = data))
}
refreshProducts()
</script>
<style scoped>
.wrapper {
  display: flex;
  justify-content: center;
  align-items: normal;
  flex-direction: column;
  width: 1000px;
  padding: 0;
  margin: auto;
}
form {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 1000px;
  flex-direction: column;
  margin-top: 50px;
}
input {
  width: 220px;
  height: 30px;
  margin: 5px auto;
  border-radius: 10px;
  padding-left: 15px;
  color: black;
  text-align: left;
  font-family: Montserrat;
  font-size: 20px;
  font-style: normal;
  font-weight: 300;
  line-height: normal;
}
textarea {
  width: 220px;
  resize: vertical;
  margin: 5px auto;
  border-radius: 10px;
  padding-left: 15px;
  color: black;
  text-align: left;
  font-family: Montserrat;
  font-size: 20px;
  font-style: normal;
  font-weight: 300;
  line-height: normal;
}
select {
  width: 220px;
  height: 30px;
  margin: 5px auto;
  border-radius: 10px;
  padding-left: 15px;
  color: black;
  text-align: left;
  font-family: Montserrat;
  font-size: 15px;
  font-style: normal;
  font-weight: 300;
  line-height: normal;
}
.product {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 1000px;
  flex-direction: row;
  padding-bottom: 20px;
}
p {
  font-family: Montserrat;
  font-size: 20px;
  font-style: normal;
  font-weight: 300;
  line-height: normal;
  color: black;
  text-align: center;
}
th {
  font-family: Montserrat;
  font-size: 20px;
  font-style: normal;
  font-weight: 600;
  line-height: normal;
  color: black;
  text-align: center;
}
td {
  font-family: Montserrat;
  font-size: 20px;
  font-style: normal;
  font-weight: 300;
  line-height: normal;
  color: black;
  text-align: center;
}
table {
  width: 1000px;
}
button {
  font-family: Montserrat;
  font-size: 20px;
  font-style: normal;
  font-weight: 300;
  line-height: normal;
  color: black;
  outline: none;
  border: none;
  background-color: rgb(171, 200, 254);
}
</style>
