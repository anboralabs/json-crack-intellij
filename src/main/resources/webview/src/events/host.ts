class HostEvent extends Event {
    message: any;

    constructor(type: string, message = {}) {
        super(type);
        this.message = message;
    }

    public toString(): string {
        return JSON.stringify({
            'event': this.type,
            ...this.message
        });
    }
}

export default HostEvent;
