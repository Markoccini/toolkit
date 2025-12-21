import type { PollResponse } from "../types/Polls/PollResponse"

export const getAllPolls = async (): Promise<PollResponse[] | null> => {
    try {
        const res = await fetch(`http://127.0.0.1:8080/api/v1/polls`)


        if (!res.ok) {
            console.error(`HTTP error: ${res.status} ${res.statusText}`);
            return null;
        }

        const data = await res.json();

        if (!Array.isArray(data)) {
            console.error("API did not return an array:", data);
            return null;
        }

        return data as PollResponse[];

    } catch (error: unknown) {
        console.error("Network or parsing error:", error);
        return null;
    }
}

const getPollById = () => {

}

const createPoll = () => {

}

const updatePoll = () => {

}

const closePoll = () => {

}
const deletePoll = () => {

}

const addChoice = () => {

}

const deleteChoice = () => {

}

const updateChoice = () => {

}

const addVote = () => {

}

const removeVote = () => {

}
