import React from "react";
import { ModalProps } from "@mantine/core";
import {
  DownloadModal,
  ImportModal,
  JWTModal,
  Modal,
  NodeModal,
  PremiumModal,
  SchemaModal,
  SettingsModal,
  ShareModal,
} from "src/containers/Modals";
import useModal from "src/store/useModal";
import { EditorMantine } from "../EditorMantine";

type ModalComponent = { key: Modal; component: React.FC<ModalProps> };

const modalComponents: ModalComponent[] = [
  { key: "import", component: ImportModal },
  { key: "download", component: DownloadModal },
  { key: "settings", component: SettingsModal },
  { key: "premium", component: PremiumModal },
  { key: "share", component: ShareModal },
  { key: "jwt", component: JWTModal },
  { key: "node", component: NodeModal },
  { key: "schema", component: SchemaModal },
];

export const ModalController = () => {
  const setVisible = useModal(state => state.setVisible);
  const modalStates = useModal(state => modalComponents.map(modal => state[modal.key]));

  return (
    <EditorMantine>
      {modalComponents.map(({ key, component }, index) => {
        const ModalComponent = component;
        const opened = modalStates[index];

        return <ModalComponent key={key} opened={opened} onClose={() => setVisible(key)(false)} />;
      })}
    </EditorMantine>
  );
};
