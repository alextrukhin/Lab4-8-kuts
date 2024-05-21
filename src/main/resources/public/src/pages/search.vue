<template>
  <div class="wrapper">
    <div class="btn-container">
      <input v-model="search" placeholder="Write your email" type="text" />
      <RouterLink :to="`/index`"><p>Exit</p></RouterLink>
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
      </template>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
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
