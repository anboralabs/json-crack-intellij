import React from "react";
import Head from "next/head";
import { useRouter } from "next/router";
import styled from "styled-components";
import { Flex, Popover, Text } from "@mantine/core";
import { MdOutlineCheckCircleOutline, MdReportGmailerrorred } from "react-icons/md";
import { TbTransform } from "react-icons/tb";
import { VscSync, VscSyncIgnored } from "react-icons/vsc";
import useFile from "src/store/useFile";
import useStored from "src/store/useStored";

const StyledBottomBar = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-top: 1px solid ${({ theme }) => theme.BACKGROUND_MODIFIER_ACCENT};
  background: ${({ theme }) => theme.BACKGROUND_TERTIARY};
  max-height: 27px;
  height: 27px;
  padding: 0 6px;

  @media screen and (max-width: 320px) {
    display: none;
  }
`;

const StyledLeft = styled.div`
  display: flex;
  align-items: center;
  justify-content: left;
  gap: 4px;

  @media screen and (max-width: 480px) {
    display: none;
  }
`;

const StyledRight = styled.div`
  display: flex;
  align-items: center;
  justify-content: right;
  gap: 4px;
`;

const StyledBottomBarItem = styled.button<{ error?: boolean }>`
  display: flex;
  align-items: center;
  gap: 4px;
  width: fit-content;
  margin: 0;
  height: 28px;
  padding: 4px;
  font-size: 12px;
  font-weight: 400;
  color: ${({ theme, error }) => (error ? theme.DANGER : theme.INTERACTIVE_NORMAL)};
  background: ${({ error }) => error && "rgba(255, 99, 71, 0.4)"};

  &:hover:not(&:disabled) {
    background-image: linear-gradient(rgba(0, 0, 0, 0.1) 0 0);
    color: ${({ theme }) => theme.INTERACTIVE_HOVER};
  }

  &:disabled {
    opacity: 0.4;
    cursor: default;
  }
`;

const StyledImg = styled.img<{ light: boolean }>`
  filter: ${({ light }) => light && "invert(100%)"};
`;

export const BottomBar = () => {
  const { query, replace } = useRouter();
  const data = useFile(state => state.fileData);
  const lightmode = useStored(state => state.lightmode);
  const toggleLiveTransform = useStored(state => state.toggleLiveTransform);
  const liveTransform = useStored(state => state.liveTransform);
  const hasChanges = useFile(state => state.hasChanges);
  const error = useFile(state => state.error);
  const setContents = useFile(state => state.setContents);

  const [isPrivate, setIsPrivate] = React.useState(false);

  React.useEffect(() => {
    setIsPrivate(data?.private ?? true);
  }, [data]);

  return (
    <StyledBottomBar>
      {data?.name && (
        <Head>
          <title>{data.name} | JSON Crack</title>
        </Head>
      )}
      <StyledLeft>
        <StyledBottomBarItem error={!!error}>
          {error ? (
            <Popover width="auto" shadow="md" position="top" withArrow>
              <Popover.Target>
                <Flex align="center" gap={2}>
                  <MdReportGmailerrorred color="red" size={16} />
                  <Text fw="bold">Invalid Format</Text>
                </Flex>
              </Popover.Target>
              <Popover.Dropdown sx={{ pointerEvents: "none" }}>
                <Text size="xs">{error}</Text>
              </Popover.Dropdown>
            </Popover>
          ) : (
            <Flex align="center" gap={2}>
              <MdOutlineCheckCircleOutline />
              <Text>Valid Format</Text>
            </Flex>
          )}
        </StyledBottomBarItem>
        {liveTransform ? (
          <StyledBottomBarItem onClick={() => toggleLiveTransform(false)}>
            <VscSync />
            <Text>Live Transform</Text>
          </StyledBottomBarItem>
        ) : (
          <StyledBottomBarItem onClick={() => toggleLiveTransform(true)}>
            <VscSyncIgnored />
            <Text>Manual Transform</Text>
          </StyledBottomBarItem>
        )}
        {!liveTransform && (
          <StyledBottomBarItem onClick={() => setContents({})}>
            <TbTransform />
            Transform
          </StyledBottomBarItem>
        )}
      </StyledLeft>
    </StyledBottomBar>
  );
};
