// https://docs.sentry.io/platforms/javascript/guides/nextjs/
import * as Sentry from "@sentry/nextjs";

if (process.env.NODE_ENV === "production") {
  Sentry.init({
    dsn: "https://4ac01fe843e04a71b93fb68fe910e4fa@o370368.ingest.sentry.io/4505519822733312",
    tracesSampleRate: 0.2,
    debug: false,
    release: `${process.env.SENTRY_RELEASE || "production"}`,
    replaysOnErrorSampleRate: 1.0,
    replaysSessionSampleRate: 0.1,
    integrations: [
      new Sentry.Replay({
        maskAllText: true,
        blockAllMedia: true,
      }),
    ],
  });
}
