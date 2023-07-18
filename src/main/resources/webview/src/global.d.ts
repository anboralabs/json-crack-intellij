declare const window: Window &
  typeof globalThis & {
    sendMessageToHost: (...args: any[]) => void;
    jsonCrackData: {
      baseUrl: string;
      file: string;
      theme: string;
    };
  };
