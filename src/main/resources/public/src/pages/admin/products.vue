<template>
  <div class="wrapper">
    <div class="btn-container">
      <RouterLink :to="`/admin/index`"><p>Exit to admin menu</p></RouterLink>
    </div>
    <div class="table-container">
      <template
        #item="{
          id,
          product_name,
          product_color,
          product_manufacturer,
          product_price,
          product_image,
          product_quantity
        }"
      >
        <div class="id-colomn">
          <p>{{ id }}</p>
        </div>
        <div class="img-colomn">
          <img src="product_image" alt="" />
        </div>
        <div class="name-colomn">
          <p>{{ product_name }}</p>
        </div>
        <div class="color-colomn">
          <p>{{ product_color }}</p>
        </div>
        <div class="manufacturer-colomn">
          <p>{{ product_manufacturer }}</p>
        </div>
        <div class="price-colomn">
          <p>{{ product_price }}</p>
        </div>
        <div class="btn-colomn"><button>Edit product - модалка</button></div>
      </template>
    </div>
    }}
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { Product } from '@/types'

const products = ref<Product[]>([])

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
  align-items: center;
  height: 100vh;
  flex-direction: colomn;
}
</style>
