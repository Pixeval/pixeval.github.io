import {createTheme} from "@fluentui/react";

export const lightTheme = createTheme({
    palette: {
        themePrimary: '#f19ec2',
        themeLighterAlt: '#fefbfd',
        themeLighter: '#fdeff5',
        themeLight: '#fbe2ed',
        themeTertiary: '#f7c5db',
        themeSecondary: '#f4abca',
        themeDarkAlt: '#da90b0',
        themeDark: '#b87a95',
        themeDarker: '#885a6e',
        neutralLighterAlt: '#f8f8f8',
        neutralLighter: '#f4f4f4',
        neutralLight: '#eaeaea',
        neutralQuaternaryAlt: '#dadada',
        neutralQuaternary: '#d0d0d0',
        neutralTertiaryAlt: '#c8c8c8',
        neutralTertiary: '#a19f9d',
        neutralSecondary: '#605e5c',
        neutralPrimaryAlt: '#3b3a39',
        neutralPrimary: '#323130',
        neutralDark: '#201f1e',
        black: '#000000',
        white: '#ffffff',
    }})

export const darkTheme = createTheme({
    palette: {
        themePrimary: '#f19ec2',
        themeLighterAlt: '#fefbfd',
        themeLighter: '#fdeff5',
        themeLight: '#fbe2ed',
        themeTertiary: '#f7c5db',
        themeSecondary: '#f4abca',
        themeDarkAlt: '#da90b0',
        themeDark: '#b87a95',
        themeDarker: '#885a6e',
        neutralLighterAlt: '#3c3b39',
        neutralLighter: '#444241',
        neutralLight: '#514f4e',
        neutralQuaternaryAlt: '#595756',
        neutralQuaternary: '#5f5e5c',
        neutralTertiaryAlt: '#7a7977',
        neutralTertiary: '#c8c8c8',
        neutralSecondary: '#d0d0d0',
        neutralPrimaryAlt: '#dadada',
        neutralPrimary: '#ffffff',
        neutralDark: '#f4f4f4',
        black: '#f8f8f8',
        white: '#323130',
    }});

export const isDarkMode = () => window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches
