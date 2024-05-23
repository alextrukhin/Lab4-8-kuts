<template>
  <RouterLink to="/admin/orders">
    <p>View orders</p>
  </RouterLink>
  <table>
    <tr>
      <th>Product name</th>
      <th>Product color</th>
      <th>Product manufacturer</th>
      <th>Product price</th>
      <th>Product image</th>
      <th>Product quantity</th>
      <th>Actions</th>
    </tr>
    <tr v-for="p in products" :key="p.id">
      <td>{{ p.name }}</td>
      <td>{{ p.color }}</td>
      <td>{{ p.manufacturer }}</td>
      <td>{{ p.price }}</td>
      <td><img :src="p.image" :alt="p.name" /></td>
      <td>{{ p.quantity }}</td>
      <td>
        <button @click="product = p">Edit</button>
        <button @click="deleteProduct(p.id)">Delete</button>
      </td>
    </tr>
  </table>
  <form @submit.prevent="saveProduct">
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
