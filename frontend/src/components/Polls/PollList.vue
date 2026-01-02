<script setup lang="ts">
import { ref, onMounted } from "vue";
import { getAllPolls } from "../../service/pollService";
import type { PollResponse } from "../../types/Polls/PollResponse";

const polls = ref<PollResponse[]>();

const fetchPollList = async () => {
    let response = await getAllPolls();

    if (response && response.length > 0) {
        polls.value = response;
    }
};

onMounted(() => {
    fetchPollList();
});
</script>

<template>
    <div class="p-4">
        <div
            class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 p-4"
        >
            <div
                v-for="poll in polls"
                :key="poll.id.toString()"
                class="rounded-xl bg-default-light shadow-lg p-5 flex flex-col border border-dark-theme-textbox"
            >
                <!-- Question -->
                <h3 class="text-lg font-bold text-gray-800 mb-4">
                    {{ poll.question }}
                </h3>

                <!-- Choices as clean boxes -->
                <div class="space-y-2 mb-4">
                    <div
                        v-for="choice in poll.choiceResponses"
                        :key="choice.id.toString()"
                        class="bg-dark-theme-textbox rounded-lg p-2 text-sm text-gray-700 border border-default-dark"
                    >
                        {{ choice.content }}
                    </div>
                </div>

                <!-- Footer -->
                <div class="mt-auto flex justify-between text-xs text-gray-500">
                    <span>{{
                        new Date(poll.createdAt).toLocaleDateString()
                    }}</span>

                    <span
                        :class="
                            poll.isClosed
                                ? 'text-red-500 font-semibold'
                                : 'text-green-600 font-semibold'
                        "
                    >
                        {{ poll.isClosed ? "Closed" : "Open" }}
                    </span>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
