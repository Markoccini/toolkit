import { ChoiceResponse } from "./ChoiceResponse";

export class PollResponse {
    id: bigint;
    question: string;
    createdAt: Date;
    choiceResponses: Array<ChoiceResponse>;
    isClosed: boolean;
}