<script setup lang="ts">
</script>

<template>
  <div class="p-4">
    <h1 class="text-3xl font-bold mb-4">
      Polls
    </h1>
    <ul>
      <li v-for="poll in polls" :key="poll.id" class="mb-2">
        <h2 class="text"></h2>
      </li>
    </ul>
  </div>
</template>

<style scoped>
</style>
