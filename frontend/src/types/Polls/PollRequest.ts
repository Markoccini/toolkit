import { ChoiceRequest } from "./ChoiceRequest";


export class PollRequest {
    question: string;
    choiceRequests: Array<ChoiceRequest>;
}