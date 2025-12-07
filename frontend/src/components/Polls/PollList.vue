<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getAllPolls } from "../../service/pollService"
import type { PollResponse } from "../../types/Polls/PollResponse"

const polls = ref<PollResponse[]>();


const fetchPollList = async () => {
  let response = await getAllPolls()

  if (response && response.length > 0) {
    polls.value = response;
  }
}

onMounted(() => {
  fetchPollList()
})
</script>


<template>
  <div class="p-4">
    <h1 class="text-3xl font-bold mb-4">
      Polls
    </h1>
    <ul>
      <li v-for="poll in polls" class="mb-2">
        <h2 class="text"> {{ poll.question }}</h2>
      </li>
    </ul>
  </div>
</template>

<style scoped>
</style>
