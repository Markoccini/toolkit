export class NotFoundError extends Error {
    status_code: number = 404;

    constructor(message: string) {
        super(message);
        this.name = "NotFoundError";

    }
}

export class ServerError extends Error {
    status_code: number = 500;

    constructor(message: string) {
        super(message);
        this.name = "ServerError";
    }
}