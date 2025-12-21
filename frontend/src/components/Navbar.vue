<script setup lang="ts">

</script>

<template>
  <header class="w-full fixed top-0 left-0 z-50">
    <nav
        class="
        mx-auto
        max-w-6xl
        bg-default-light backdrop-blur-md
        rounded-b-xl
        shadow-lg
        px-8
        py-4
        flex
        items-center
        justify-between
      "
    >
      <!-- Left: Home -->
      <RouterLink
          to="/"
          class="
          text-gray-700
          font-semibold
          hover:text-blue-600
          transition
        "
      >
        Home
      </RouterLink>

      <!-- Center: Polls -->
      <RouterLink
          to="/polls"
          class="
          text-gray-900
          font-bold
          text-xl
          tracking-wide
          hover:text-blue-500
          transition
        "
      >
        Polls
      </RouterLink>

      <!-- Right: Fake Logout -->
      <RouterLink
          to="/"
          class="
          text-red-600
          font-medium
          hover:text-red-800
          transition
        "
      >
        Logout
      </RouterLink>
    </nav>
  </header>

  <!-- spacer so content is not hidden under fixed navbar -->
  <div class="h-[92px]"></div>
</template>

<style scoped>
</style>
